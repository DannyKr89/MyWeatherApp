package com.dk.myweatherapp.data.repository

import com.dk.myweatherapp.BuildConfig
import com.dk.myweatherapp.data.RequestApi
import com.dk.myweatherapp.data.model.CitiesLocation
import com.dk.myweatherapp.data.model.City
import com.dk.myweatherapp.data.model.getRussianCities
import com.dk.myweatherapp.data.model.getWorldCities
import com.dk.myweatherapp.data.model.weather_dto.Weather
import com.dk.myweatherapp.domain.Repository
import retrofit2.Callback

class RepositoryImpl : Repository {

    override fun getWeatherList(location: CitiesLocation): List<City> {
        return when (location) {
            CitiesLocation.RussianCities -> {
                getRussianCities()
            }
            CitiesLocation.WorldCities -> {
                getWorldCities()
            }
        }
    }

    override fun getWeatherFromAPI(city: City, callback: Callback<Weather>) {
        return RequestApi.create().getDetailWeather(BuildConfig.CONSUMER_KEY, city.lat, city.lon)
            .enqueue(callback)
    }
}

