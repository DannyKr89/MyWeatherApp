package com.dk.myweatherapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dk.myweatherapp.data.model.CitiesLocation
import com.dk.myweatherapp.data.model.getRussianCities
import com.dk.myweatherapp.data.repository.RepositoryImpl
import com.dk.myweatherapp.domain.WeatherInteractor

class WeatherListViewModel(
    private var getNextLoc: MutableLiveData<Boolean> = MutableLiveData(),
    private var getRequestWeatherList: MutableLiveData<State> = MutableLiveData()
) : ViewModel() {
    init {
        getNextLocation().value = true
        getRequestWeatherList.value = State.SuccessWeatherList(getRussianCities())
    }

    private val repository = RepositoryImpl()
    private val weatherInteractor = WeatherInteractor(repository)

    fun getNextLocation(): MutableLiveData<Boolean> {
        return getNextLoc
    }

    fun changeLocation(nextLoc: Boolean) {
        val location = getNextLocation().value!!
        getNextLoc.value = !location
    }

    fun getWeatherListState() = getRequestWeatherList


    private fun getWeatherListRequestState(location: CitiesLocation){
        getRequestWeatherList.value = State.Loading
        val weatherList = weatherInteractor.getList(location)
        getRequestWeatherList.value = State.SuccessWeatherList(weatherList)
    }

    fun getRussiansCitiesList() {
        getWeatherListRequestState(CitiesLocation.RussianCities)
    }

    fun getWorldCitiesList() {
        getWeatherListRequestState(CitiesLocation.WorldCities)
    }

}
