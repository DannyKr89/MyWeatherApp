package com.dk.myweatherapp.data.repository

import androidx.lifecycle.LiveData
import com.dk.myweatherapp.data.common.convertWeatherToHistoryWeather
import com.dk.myweatherapp.data.model.weather_dto.Weather
import com.dk.myweatherapp.data.room.HistoryDao
import com.dk.myweatherapp.data.room.HistoryWeather
import com.dk.myweatherapp.domain.GetLocalDBRepository

class LocalRepositoryImpl(private val localDBSource: HistoryDao) : GetLocalDBRepository {
    override fun getAllHistory(): LiveData<List<HistoryWeather>> {
        return localDBSource.all()
    }

    override fun saveToDB(weather: Weather) {
        localDBSource.insertWeather(convertWeatherToHistoryWeather(weather))
    }
}
