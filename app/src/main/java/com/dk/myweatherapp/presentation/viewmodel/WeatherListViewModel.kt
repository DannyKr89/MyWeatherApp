package com.dk.myweatherapp.presentation.viewmodel

import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dk.myweatherapp.R
import com.dk.myweatherapp.data.MINIMAL_DISTANCE
import com.dk.myweatherapp.data.REFRESH_PERIOD
import com.dk.myweatherapp.data.model.CitiesLocation
import com.dk.myweatherapp.data.model.City
import com.dk.myweatherapp.data.repository.GetListRepositoryImpl
import com.dk.myweatherapp.domain.GetListInteractor
import java.io.IOException

class WeatherListViewModel(
    private val getRequestWeatherList: MutableLiveData<List<City>> = MutableLiveData(),
    private val repository: GetListRepositoryImpl = GetListRepositoryImpl(),
    private val getListInteractor: GetListInteractor = GetListInteractor(repository),
    private val getSearchAddressState: MutableLiveData<AddressState> = MutableLiveData(),
    private val getMyLocationState: MutableLiveData<LocationState> = MutableLiveData()
) : ViewModel() {

    fun getWeatherListState() = getRequestWeatherList


    fun getWeatherListRequestState(bool: Boolean) {
        val location = if (bool) {
            CitiesLocation.WorldCities
        } else {
            CitiesLocation.RussianCities
        }
        val weatherList = getListInteractor.getList(location)
        getRequestWeatherList.value = weatherList
    }

    fun getSearchLocationState() = getSearchAddressState

    fun setSearchLocationDefaultState() {
        getSearchAddressState.value = AddressState.Default
    }

    @Suppress("DEPRECATION")
    fun getSearchRequest(lat: Double, lon: Double, geocoder: Geocoder) {
        getSearchAddressState.value = AddressState.Loading
        if (lat in 0.0..90.0 && lat in 0.0..180.0) {
            val location = Location("me").apply {
                latitude = lat
                longitude = lon
            }
            Thread {
                try {
                    val address = geocoder.getFromLocation(lat, lon, 1)
                    if (!address.isNullOrEmpty()) {
                        getSearchAddressState.postValue(
                            AddressState.Success(
                                address[0].getAddressLine(
                                    0
                                ), location
                            )
                        )
                    } else {
                        getSearchAddressState.postValue(
                            AddressState.Error(
                                R.string.loading_error,
                                R.string.bad_address
                            )
                        )
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }.start()
        } else {
            getSearchAddressState.postValue(
                AddressState.Error(
                    R.string.loading_error,
                    R.string.bad_coords
                )
            )
        }
    }

    fun getMyLocationState() = getMyLocationState

    fun setMyLocationStateDefault() {
        getMyLocationState.value = LocationState.Default
    }

    @Suppress("DEPRECATION")
    @SuppressLint("MissingPermission")
    fun getMyLocationRequest(locationManager: LocationManager) {
        getMyLocationState.value = LocationState.Loading
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            val provider = locationManager.getProvider(LocationManager.GPS_PROVIDER)
            provider?.let {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    REFRESH_PERIOD,
                    MINIMAL_DISTANCE,
                    locationListener
                )
            }
        } else {
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location == null) {
                getMyLocationState.postValue(
                    LocationState.Error(
                        R.string.dialog_title_gps_turned_off,
                        R.string.dialog_message_last_location_unknown
                    )
                )
            } else {
                getMyLocationState.postValue(
                    LocationState.Success(
                        location,
                        R.string.dialog_message_last_known_location
                    )
                )
            }
        }
    }

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            getMyLocationState.postValue(LocationState.Success(location))
        }

        override fun onProviderEnabled(provider: String) {}

        override fun onProviderDisabled(provider: String) {}

        @Deprecated("Deprecated in Java")
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }
    }

}
