package com.dk.myweatherapp.data.repository

import com.dk.myweatherapp.BuildConfig
import com.dk.myweatherapp.data.RequestApi
import com.dk.myweatherapp.data.model.City
import com.dk.myweatherapp.data.model.weather_dto.Weather
import com.dk.myweatherapp.domain.GetDetailRepository
import retrofit2.Callback

class GetDetailRepositoryImpl: GetDetailRepository {
    override fun getWeatherFromAPI(city: City, callback: Callback<Weather>) {
        return RequestApi.create().getDetailWeather(BuildConfig.CONSUMER_KEY, city.lat, city.lon)
            .enqueue(callback)
    }
}