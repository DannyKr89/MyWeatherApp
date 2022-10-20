package com.dk.myweatherapp.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dk.myweatherapp.domain.RepositoryImpl
import com.dk.myweatherapp.model.Weather

class WeatherViewModel(
    private val getWeather:MutableLiveData<Weather> = MutableLiveData()
) : ViewModel() {

    private val repository = RepositoryImpl()


    fun getWeather():MutableLiveData<Weather>{
        return getWeather
    }


    fun request(weather: Weather) {
        getWeather.value = repository.getWeather(weather)
    }
}