package com.dk.myweatherapp.data.repository

import com.dk.myweatherapp.data.ICON_URL
import com.dk.myweatherapp.data.model.weather_dto.Weather
import com.dk.myweatherapp.data.room.HistoryDao
import com.dk.myweatherapp.data.room.HistoryWeather
import com.dk.myweatherapp.domain.LocalRepository

class LocalRepositoryImpl(private val localDataSource: HistoryDao) : LocalRepository {
    override fun getAllHistory(): List<HistoryWeather> {
        return localDataSource.all()
    }

    override fun saveToDB(weather: Weather) {
        localDataSource.insertWeather(convertWeatherToHistoryWeather(weather))
    }

    private fun convertWeatherToHistoryWeather(weather: Weather): HistoryWeather {
        return HistoryWeather(
            0, weather.city.name, weather.fact.temp, weather.fact.condition, ICON_URL + weather.fact.icon + ".svg"
        )
    }
}
