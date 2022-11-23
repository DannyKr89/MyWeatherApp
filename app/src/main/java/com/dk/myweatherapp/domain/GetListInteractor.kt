package com.dk.myweatherapp.domain

import com.dk.myweatherapp.data.model.CitiesLocation
import com.dk.myweatherapp.data.model.City

class GetListInteractor(private val getListRepository: GetListRepository) {

    fun getList(location: CitiesLocation): List<City> {
        return getListRepository.getWeatherList(location)
    }

}