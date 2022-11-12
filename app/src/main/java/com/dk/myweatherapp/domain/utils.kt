package com.dk.myweatherapp.domain

import com.dk.myweatherapp.BuildConfig
import com.dk.myweatherapp.model.City
import com.dk.myweatherapp.model.weather_dto.WeatherDTO
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

const val BASE_URL = "https://api.weather.yandex.ru/v2/informers?" //TODO Так правильно?

fun requestWeatherDTO(city: City): WeatherDTO {


    val url =
        URL(BASE_URL + "lat=${city.lat}&lon=${city.lon}")
    val connection = url.openConnection() as HttpsURLConnection
    connection.apply {
        addRequestProperty("X-Yandex-API-Key", BuildConfig.CONSUMER_KEY)
        readTimeout = 10000
    }
    return try {
        val inputStream = connection.inputStream
        val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
        val weatherDTO = Gson().fromJson(inputStreamReader, WeatherDTO::class.java)
        inputStreamReader.close()
        inputStream.close()
        weatherDTO
    } catch (e:Exception){
        println()
        WeatherDTO()
    } finally {
        connection.disconnect()
    }

}

