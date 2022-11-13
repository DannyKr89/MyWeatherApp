package com.dk.myweatherapp.domain

import com.dk.myweatherapp.model.weather_dto.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RequestApi {
    @GET("v2/informers?")
    fun getDetailWeather(
        @Header("X-Yandex-API-Key") token: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Call<Weather>
}