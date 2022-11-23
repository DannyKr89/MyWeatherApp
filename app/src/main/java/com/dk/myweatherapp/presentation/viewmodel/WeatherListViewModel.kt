package com.dk.myweatherapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dk.myweatherapp.data.model.CitiesLocation
import com.dk.myweatherapp.data.model.City
import com.dk.myweatherapp.data.repository.GetListRepositoryImpl
import com.dk.myweatherapp.domain.GetListInteractor

class WeatherListViewModel(
    private var getRequestWeatherList: MutableLiveData<List<City>> = MutableLiveData(),
    private val repository: GetListRepositoryImpl = GetListRepositoryImpl(),
    private val getListInteractor: GetListInteractor = GetListInteractor(repository)
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

}
