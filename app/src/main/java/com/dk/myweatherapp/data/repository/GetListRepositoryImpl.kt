package com.dk.myweatherapp.data.repository

import com.dk.myweatherapp.data.model.CitiesLocation
import com.dk.myweatherapp.data.model.City
import com.dk.myweatherapp.data.model.getRussianCities
import com.dk.myweatherapp.data.model.getWorldCities
import com.dk.myweatherapp.domain.GetListRepository

class GetListRepositoryImpl : GetListRepository {

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
}

