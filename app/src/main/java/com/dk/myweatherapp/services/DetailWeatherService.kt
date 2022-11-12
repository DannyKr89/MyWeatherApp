@file:Suppress("DEPRECATION")

package com.dk.myweatherapp.services

import android.app.IntentService
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.dk.myweatherapp.BuildConfig
import com.dk.myweatherapp.model.City
import com.dk.myweatherapp.model.weather_dto.Weather
import com.dk.myweatherapp.view.DetailWeatherFragment.Companion.CITY
import com.dk.myweatherapp.view.DetailWeatherFragment.Companion.ERROR
import com.dk.myweatherapp.view.DetailWeatherFragment.Companion.LOAD_RESULT
import com.dk.myweatherapp.view.DetailWeatherFragment.Companion.SUCCESS
import com.dk.myweatherapp.view.DetailWeatherFragment.Companion.WEATHER
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection


const val BASE_URL = "https://api.weather.yandex.ru/v2/informers?" //TODO Так правильно?

class DetailWeatherService(name: String = "DetailService") : IntentService(name) {

    private val broadcastIntent = Intent("weather")

    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) {
            println("Как так? Пустой интент!")
        } else {
            val city = intent.getParcelableExtra<City>(CITY)!!
            requestWeatherDTO(city)
        }
    }

    private fun requestWeatherDTO(city: City) {
        val url = URL(BASE_URL + "lat=${city.lat}&lon=${city.lon}")
        val connection = url.openConnection() as HttpsURLConnection
        connection.apply {
            addRequestProperty("X-Yandex-API-Key", BuildConfig.CONSUMER_KEY)
            readTimeout = 10000
        }
        try {
            val inputStream = connection.inputStream
            val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
            val weather = Gson().fromJson(inputStreamReader, Weather::class.java)
            inputStreamReader.close()
            inputStream.close()
            onSuccessResponse(weather)
        } catch (e: Exception) {
            println(e.message)
            onErrorResponse(Weather())
        } finally {
            connection.disconnect()
        }
    }

    private fun loadResult( result: String){
        broadcastIntent.putExtra(LOAD_RESULT, result)
    }

    private fun onErrorResponse(weather: Weather) {
        loadResult(ERROR)
        broadcastIntent.putExtra(WEATHER, weather)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onSuccessResponse(weather: Weather?) {
        loadResult(SUCCESS)
        broadcastIntent.putExtra(WEATHER, weather)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }
}