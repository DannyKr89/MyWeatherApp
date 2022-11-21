package com.dk.myweatherapp.data

import com.dk.myweatherapp.data.model.weather_dto.Weather
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    companion object{
        fun create(): RequestApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(RequestApi::class.java)
        }
    }
}