package com.dk.myweatherapp.domain

import com.dk.myweatherapp.data.model.CitiesLocation
import com.dk.myweatherapp.data.model.City
import com.dk.myweatherapp.data.model.weather_dto.Weather
import retrofit2.Callback

class WeatherInteractor(private val repository: Repository) {

    fun getList(location: CitiesLocation): List<City>{
        return repository.getWeatherList(location)
    }

    fun getDetail(city: City, callback: Callback<Weather>){
        return repository.getWeatherFromAPI(city, callback)
    }


}