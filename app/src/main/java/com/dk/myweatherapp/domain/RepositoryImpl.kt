package com.dk.myweatherapp.domain

import com.dk.myweatherapp.model.CitiesLocation
import com.dk.myweatherapp.model.Weather
import com.dk.myweatherapp.model.getRussianCities
import com.dk.myweatherapp.model.getWorldCities

class RepositoryImpl : RepositoryWeatherListCities, RepositoryNextLoc {


    override fun getWeatherList(location: CitiesLocation): List<Weather> {
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
}

