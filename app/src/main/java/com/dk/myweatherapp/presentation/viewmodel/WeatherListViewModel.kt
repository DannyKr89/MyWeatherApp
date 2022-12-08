package com.dk.myweatherapp.presentation.viewmodel

import android.content.Context
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dk.myweatherapp.data.model.CitiesLocation
import com.dk.myweatherapp.data.model.City
import com.dk.myweatherapp.data.repository.GetListRepositoryImpl
import com.dk.myweatherapp.domain.GetListInteractor
import java.io.IOException

class WeatherListViewModel(
    private val getRequestWeatherList: MutableLiveData<List<City>> = MutableLiveData(),
    private val repository: GetListRepositoryImpl = GetListRepositoryImpl(),
    private val getListInteractor: GetListInteractor = GetListInteractor(repository),
    private val getSearchLocationState: MutableLiveData<AddressState> = MutableLiveData()
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

    fun getSearchLocationState() = getSearchLocationState

    fun setSearchLocationDefaultState() {
        getSearchLocationState.value = AddressState.Default
    }

    @Suppress("DEPRECATION")
    fun getSearchRequest(lat: Double, lon: Double, context: Context) {
        getSearchLocationState.value = AddressState.Loading
        val geocoder = Geocoder(context)
        if (lat in 0.0..90.0 && lat in 0.0..180.0) {
            val location = Location("me").apply {
                latitude = lat
                longitude = lon
            }
            Thread {
                try {
                    val address = geocoder.getFromLocation(lat, lon, 1)
                    if (!address.isNullOrEmpty()) {
                        getSearchLocationState.postValue(
                            AddressState.Success(
                                address[0].getAddressLine(
                                    0
                                ), location
                            )
                        )
                    } else {
                        getSearchLocationState.postValue(AddressState.Error("Неверный адресс"))
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    getSearchLocationState.postValue(e.message?.let { AddressState.Error(it) })
                }

            }.start()
        } else {
            getSearchLocationState.postValue(AddressState.Error("Неверные координаты"))
        }

    }
}
