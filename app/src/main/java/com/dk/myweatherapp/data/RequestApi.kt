package com.dk.myweatherapp.data

import com.dk.myweatherapp.data.model.weather_dto.Weather
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface RequestApi {
    @GET("v2/informers?")
    fun getDetailWeather(
        @Header("X-Yandex-API-Key") token: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Call<Weather>

    companion object {

        private val okHttpClient = OkHttpClient.Builder() // TODO не работает почему то
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        fun create(): RequestApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(RequestApi::class.java)
        }

    }
}