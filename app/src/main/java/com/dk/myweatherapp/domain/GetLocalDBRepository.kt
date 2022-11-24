package com.dk.myweatherapp.domain

import androidx.lifecycle.LiveData
import com.dk.myweatherapp.data.model.weather_dto.Weather
import com.dk.myweatherapp.data.room.HistoryWeather

interface GetLocalDBRepository {
    fun getAllHistory(): LiveData<List<HistoryWeather>>
    fun saveToDB(weather: Weather)
}