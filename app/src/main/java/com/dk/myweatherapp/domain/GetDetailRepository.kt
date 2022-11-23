package com.dk.myweatherapp.domain

import com.dk.myweatherapp.data.model.City
import com.dk.myweatherapp.data.model.weather_dto.Weather
import retrofit2.Callback

interface GetDetailRepository {
    fun getWeatherFromAPI(city: City, callback: Callback<Weather>)
}