package com.dk.myweatherapp.domain

import com.dk.myweatherapp.data.model.weather_dto.Weather
import com.dk.myweatherapp.data.room.HistoryWeather

class GetLocalDBInteractor(private val getLocalDBRepository: GetLocalDBRepository) {
    fun getAllHistory(): List<HistoryWeather> {
        return getLocalDBRepository.getAllHistory()
    }

    fun saveToDB(weather: Weather) {
        getLocalDBRepository.saveToDB(weather)
    }
}