package com.dk.myweatherapp.domain

import com.dk.myweatherapp.data.model.City
import com.dk.myweatherapp.data.model.weather_dto.Weather
import retrofit2.Callback

class GetDetailInteractor(private val getDetailRepository: GetDetailRepository) {
    fun getDetail(city: City, callback: Callback<Weather>){
        return getDetailRepository.getWeatherFromAPI(city, callback)
    }
}