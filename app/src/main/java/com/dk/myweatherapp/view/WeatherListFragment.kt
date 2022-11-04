package com.dk.myweatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.dk.myweatherapp.R
import com.dk.myweatherapp.databinding.FragmentWeatherListBinding
import com.dk.myweatherapp.model.City
import com.dk.myweatherapp.model.Weather
import com.dk.myweatherapp.viewmodel.State
import com.dk.myweatherapp.viewmodel.WeatherViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_detail_weather.*

@Suppress("UNUSED_EXPRESSION")
class WeatherListFragment : Fragment() {
    private var _binding: FragmentWeatherListBinding? = null
    private val binding: FragmentWeatherListBinding
        get() {
            return _binding!!
        }
    private val viewModel: WeatherViewModel by activityViewModels()
    private lateinit var adapter: WeatherListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getWeatherListState().observe(viewLifecycleOwner) {
            when (it) {
                is State.Error -> {
                    hideProgressbar()
                    binding.root.ReloadSnackBar(
                        getString(R.string.loading_error),
                        Snackbar.LENGTH_INDEFINITE,
                        getString(R.string.repeat)
                    ) {
                        locationCondition()
                    }.show()

                }
                is State.Loading -> {
                    showProgressbar()
                }
                is State.SuccessWeatherList -> {
                    hideProgressbar()
                    renderList(it.weatherList)
                }
                is State.SuccessWeather -> {}
            }
        }

        viewModel.getNextLocation().observe(viewLifecycleOwner) {
            if (it) {
                binding.fabChangeList.setImageResource(R.drawable.russia)
            } else {
                binding.fabChangeList.setImageResource(R.drawable.earth)
            }
        }


        binding.fabChangeList.setOnClickListener {
            viewModel.changeLocation(viewModel.getNextLocation().value!!)
            locationCondition()
        }

        with(binding) {
            btnSearch.setOnClickListener {
                if (latitude.text.isNotEmpty() && longitude.text.isNotEmpty()) {
                    val weather = Weather(
                        City(
                            "Свои координаты",
                            latitude.text.toString().toDouble(),
                            longitude.text.toString().toDouble()
                        )
                    )
                    findNavController().navigate(R.id.action_weatherListFragment_to_detailWeatherFragment,
                        Bundle().apply {
                            putParcelable(
                                DetailWeatherFragment.WEATHER, weather
                            )
                            putString("cityName", weather.city.name)
                        })
                } else {
                    Toast.makeText(
                        super.getContext(),
                        "Введите широту и долготу",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    private fun showProgressbar() {
        with(binding) {
            progress.visibility = View.VISIBLE
            rvWeatherList.visibility = View.GONE
            fabChangeList.visibility = View.GONE
        }
    }

    private fun hideProgressbar() {
        with(binding) {
            progress.visibility = View.GONE
            rvWeatherList.visibility = View.VISIBLE
            fabChangeList.visibility = View.VISIBLE
        }
    }

    private fun locationCondition() {
        if (viewModel.getNextLocation().value!!) {
            viewModel.getRussiansCitiesList()
        } else {
            viewModel.getWorldCitiesList()
        }
    }

    private fun renderList(weathers: List<Weather>) {
        adapter = WeatherListAdapter(object : OnItemViewClick {
            override fun onWeatherClick(weather: Weather) {
                findNavController().navigate(R.id.action_weatherListFragment_to_detailWeatherFragment,
                    Bundle().apply {
                        putParcelable(
                            DetailWeatherFragment.WEATHER, weather
                        )
                        putString("cityName", weather.city.name)
                    })
            }
        }).also {
            it.setWeatherList(weathers)
        }
        binding.rvWeatherList.adapter = adapter
    }

    interface OnItemViewClick {
        fun onWeatherClick(weather: Weather)
    }

    override fun onDestroyView() {
        _binding = null
        adapter.removeListener()
        super.onDestroyView()
    }


    private fun View.ReloadSnackBar(
        error: String, duration: Int, actionString: String, function: (v: View) -> Unit
    ): Snackbar {
        return Snackbar.make(binding.root, error, duration).setAction(actionString, function)
    }
}

