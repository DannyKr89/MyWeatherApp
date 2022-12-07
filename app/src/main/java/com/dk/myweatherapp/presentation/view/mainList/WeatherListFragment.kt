package com.dk.myweatherapp.presentation.view.mainList

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.dk.myweatherapp.R
import com.dk.myweatherapp.data.*
import com.dk.myweatherapp.data.model.City
import com.dk.myweatherapp.databinding.FragmentWeatherListBinding
import com.dk.myweatherapp.presentation.viewmodel.WeatherListViewModel
import java.io.IOException


class WeatherListFragment : Fragment() {
    private var _binding: FragmentWeatherListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WeatherListViewModel by activityViewModels()
    private val adapter = WeatherListAdapter()
    private lateinit var sharedPref: SharedPreferences
    private var isDataSetWorld = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadLastList()

        init()
    }

    private fun loadLastList() {
        sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)!!
        isDataSetWorld = sharedPref.getBoolean(LIST_KEY, false)
        changeList()
    }

    @Suppress("DEPRECATION")
    private fun init() {
        viewModel.getWeatherListState().observe(viewLifecycleOwner) {
            renderList(it)
        }

        with(binding) {

            fabChangeList.setOnClickListener {
                isDataSetWorld = !isDataSetWorld
                changeList()
            }

            fabCurrentLocation.setOnClickListener {
                checkPermission()
            }

            btnSearch.setOnClickListener {
                if (latitude.text.isNotEmpty() && longitude.text.isNotEmpty()) {
                    val geocoder = Geocoder(requireContext())
                    val lat = latitude.text.toString().toDouble()
                    val lon = longitude.text.toString().toDouble()
                    val location = Location("me").also {
                        it.latitude = lat
                        it.longitude = lon
                    }
                    Thread {
                        val address = geocoder.getFromLocation(lat, lon, 1)
                        btnSearch.post {
                            if (!address.isNullOrEmpty()) {
                                showAddressDialog(address[0].getAddressLine(0), location)
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Неверный адресс",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }.start()

                } else {
                    Toast.makeText(
                        requireContext(),
                        "Введите широту и долготу",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun getDetails(city: City) {
        findNavController().navigate(
            R.id.action_weatherListFragment_to_detailWeatherFragment,
            Bundle().apply {
                putParcelable(
                    CITY, city
                )
                putString("cityName", city.name)
            })
    }

    private fun changeList() {
        if (isDataSetWorld) {
            binding.fabChangeList.setImageResource(R.drawable.earth)
        } else {
            binding.fabChangeList.setImageResource(R.drawable.russia)
        }
        viewModel.getWeatherListRequestState(isDataSetWorld)
        sharedPref.edit().putBoolean(LIST_KEY, isDataSetWorld).apply()
    }

    private fun renderList(weathers: List<City>) {
        adapter.submitList(weathers)
        adapter.listener = {
            getDetails(it)
        }
        binding.rvWeatherList.adapter = adapter
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getLocation()
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            showRationaleDialog()
        } else {
            requestPermission()
        }
    }

    @Suppress("DEPRECATION")
    private fun getLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val locationManager =
                requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                val provider = locationManager.getProvider(LocationManager.GPS_PROVIDER)
                provider?.let {
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        REFRESH_PERIOD,
                        MINIMAL_DISTANCE,
                        object : LocationListener {
                            override fun onLocationChanged(location: Location) {
                                getAddressAsync(location)
                            }
                            override fun onProviderEnabled(provider: String) {}

                            override fun onProviderDisabled(provider: String) {}

                            @Deprecated("Deprecated in Java")
                            override fun onStatusChanged(
                                provider: String?,
                                status: Int,
                                extras: Bundle?
                            ) {}
                        }
                    )
                }
            } else {
                val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (location == null) {
                    showDialog(
                        getString(R.string.dialog_title_gps_turned_off),
                        getString(R.string.dialog_message_last_location_unknown)
                    )
                } else {
                    getAddressAsync(location)
                    showDialog(
                        getString(R.string.dialog_title_gps_turned_off),
                        getString(R.string.dialog_message_last_known_location)
                    )
                }
            }
        } else {
            showRationaleDialog()
        }
    }

    @Suppress("DEPRECATION")
    private fun getAddressAsync(location: Location) {
        val geoCoder = Geocoder(requireContext())
        Thread {
            try {
                val addresses = geoCoder.getFromLocation(location.latitude, location.longitude, 1)
                binding.fabCurrentLocation.post {
                    if (addresses != null && addresses.isNotEmpty()) {
                            showAddressDialog(addresses[0].getAddressLine(0), location)
                        }
                    }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun showAddressDialog(address: String, location: Location) {
        val city = City(
            address,
            location.latitude,
            location.longitude
        )
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_address_title))
            .setMessage(address)
            .setPositiveButton(getString(R.string.dialog_address_get_weather)) { _, _ ->
                getDetails(city)
            }
            .setNegativeButton(getString(R.string.dialog_button_close)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun showRationaleDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_rationale_title))
            .setMessage(getString(R.string.dialog_rationale_meaasge))
            .setPositiveButton(getString(R.string.dialog_rationale_give_access)) { _, _ ->
                requestPermission()
            }
            .setNegativeButton(getString(R.string.dialog_rationale_decline)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    @Suppress("DEPRECATION")
    private fun requestPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION)
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        checkPermissionsResult(requestCode, grantResults)
    }

    private fun checkPermissionsResult(requestCode: Int, grantResults: IntArray) {
        if (requestCode == REQUEST_LOCATION) {
            var grantedPermission = 0
            if (grantResults.isNotEmpty()) {
                for (i in grantResults) {
                    if (i == PackageManager.PERMISSION_GRANTED) {
                        grantedPermission++
                    }
                }
                if (grantResults.size == grantedPermission) {
                    getLocation()
                } else {
                    showDialog(
                        getString(R.string.dialog_title_no_gps),
                        getString(R.string.dialog_message_no_gps)
                    )
                }
            }
        }
    }

    private fun showDialog(title: String, message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setNegativeButton(getString(R.string.dialog_button_close)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}

