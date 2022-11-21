package com.dk.myweatherapp.domain

import com.dk.myweatherapp.data.model.CitiesLocation
import com.dk.myweatherapp.data.model.City
import com.dk.myweatherapp.data.model.weather_dto.Weather
import retrofit2.Callback

interface Repository {
    fun getWeatherList(location: CitiesLocation): List<City>
    fun getWeatherFromAPI(city: City, callback: Callback<Weather>)
}