package com.dk.myweatherapp.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dk.myweatherapp.domain.RepositoryImpl
import com.dk.myweatherapp.model.CitiesLocation
import com.dk.myweatherapp.model.Weather

class WeatherViewModel(
    /*private val getWeather: MutableLiveData<Weather> = MutableLiveData(),*/
    private var getWeatherList: MutableLiveData<List<Weather>> = MutableLiveData(),
    private var getNextLoc: MutableLiveData<Boolean> = MutableLiveData()
) : ViewModel() {
    init {
        getNextLocation().value = true
    }

    private val repository = RepositoryImpl()
    
    
    fun getNextLocation():MutableLiveData<Boolean>{
        return getNextLoc
    }
    
    
    fun changeLocation(nextLoc: Boolean){
        getNextLoc.value = repository.getNextLoc(nextLoc)
    }


    fun getWeatherList(): MutableLiveData<List<Weather>> {
        return getWeatherList
    }

    private fun requestList(location: CitiesLocation) {
        getWeatherList.value = repository.getWeatherList(location)
    }

    fun getRussiansCitiesList() {
        requestList(CitiesLocation.RussianCities)
    }

    fun getWorldCitiesList() {
        requestList(CitiesLocation.WorldCities)
    }

}
