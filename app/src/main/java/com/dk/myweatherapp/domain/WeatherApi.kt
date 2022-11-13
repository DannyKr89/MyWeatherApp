package com.dk.myweatherapp.domain

import com.dk.myweatherapp.BuildConfig
import com.dk.myweatherapp.model.City
import com.dk.myweatherapp.model.weather_dto.Weather
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.weather.yandex.ru/"

class WeatherApi {
    private val weatherApi =
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        ).build().create(RequestApi::class.java)

    fun getWeatherDetails(city: City, callback: Callback<Weather>) {
        weatherApi.getDetailWeather(BuildConfig.CONSUMER_KEY, city.lat, city.lon).enqueue(callback)
    }
}