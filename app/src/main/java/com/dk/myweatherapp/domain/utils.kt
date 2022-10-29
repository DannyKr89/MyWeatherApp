package com.dk.myweatherapp.domain

import com.dk.myweatherapp.BuildConfig
import com.dk.myweatherapp.model.Weather
import com.dk.myweatherapp.model.weather_dto.WeatherDTO
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

fun requestWeatherDTO(weather: Weather): WeatherDTO {

    val url =
        URL("https://api.weather.yandex.ru/v2/informers?lat=${weather.city.lat}&lon=${weather.city.lon}")
    val connection = url.openConnection() as HttpsURLConnection
    connection.apply {
        addRequestProperty("X-Yandex-API-Key", BuildConfig.CONSUMER_KEY)
        readTimeout = 10000
    }

    val inputStream = connection.inputStream
    val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
    val weatherDTO = Gson().fromJson(inputStreamReader, WeatherDTO::class.java)
    inputStreamReader.close()
    inputStream.close()
    connection.disconnect()
    return weatherDTO
}

