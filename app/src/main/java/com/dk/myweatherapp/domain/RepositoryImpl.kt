package com.dk.myweatherapp.domain

import com.dk.myweatherapp.model.CitiesLocation
import com.dk.myweatherapp.model.City
import com.dk.myweatherapp.model.getRussianCities
import com.dk.myweatherapp.model.getWorldCities
import com.dk.myweatherapp.model.weather_dto.Weather

class RepositoryImpl : RepositoryWeatherListCities, RepositoryNextLoc, RepositoryWeather {


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

    override fun getWeather(city: City): Weather {
        return requestWeatherDTO(city)
    }
}

