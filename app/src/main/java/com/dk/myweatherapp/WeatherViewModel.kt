package com.dk.myweatherapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dk.myweatherapp.model.Weather

class WeatherViewModel(
    private val liveData: MutableLiveData<Weather> = MutableLiveData()
) : ViewModel() {

    private val repository = RepositoryImpl()

    fun getData(): MutableLiveData<Weather> {
        return liveData
    }

    fun request(weather: Weather) {
        liveData.value = repository.getWeather(weather)
    }
}