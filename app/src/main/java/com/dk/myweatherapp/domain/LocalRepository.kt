package com.dk.myweatherapp.domain

import com.dk.myweatherapp.data.model.weather_dto.Weather
import com.dk.myweatherapp.data.room.HistoryWeather

interface LocalRepository {
    fun getAllHistory(): List<HistoryWeather>
    fun saveToDB(weather: Weather)
}