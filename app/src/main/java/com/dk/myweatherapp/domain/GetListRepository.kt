package com.dk.myweatherapp.domain

import com.dk.myweatherapp.data.model.CitiesLocation
import com.dk.myweatherapp.data.model.City

interface GetListRepository {
    fun getWeatherList(location: CitiesLocation): List<City>
}

