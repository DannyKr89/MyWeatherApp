package com.dk.myweatherapp.domain

import com.dk.myweatherapp.BuildConfig
import com.dk.myweatherapp.model.CitiesLocation
import com.dk.myweatherapp.model.City
import com.dk.myweatherapp.model.getRussianCities
import com.dk.myweatherapp.model.getWorldCities
import com.dk.myweatherapp.model.weather_dto.Weather
import retrofit2.Callback

class RepositoryImpl() : RepositoryWeatherListCities,
    RepositoryNextLoc, RepositoryWeather {


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

    override fun getNextLoc(nextLoc: Boolean): Boolean {
        return !nextLoc
    }

    override fun getWeatherFromAPI(city: City, callback: Callback<Weather>) {
        return RequestApi.create().getDetailWeather(BuildConfig.CONSUMER_KEY, city.lat, city.lon)
            .enqueue(callback)
    }
}

