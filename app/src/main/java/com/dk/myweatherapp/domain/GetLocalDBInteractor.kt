package com.dk.myweatherapp.domain

import androidx.lifecycle.LiveData
import com.dk.myweatherapp.data.model.weather_dto.Weather
import com.dk.myweatherapp.data.room.HistoryWeather

class GetLocalDBInteractor(private val getLocalDBRepository: GetLocalDBRepository) {
    fun getAllHistory(): LiveData<List<HistoryWeather>> {
        return getLocalDBRepository.getAllHistory()
    }

    fun saveToDB(weather: Weather) {
        getLocalDBRepository.saveToDB(weather)
    }

    fun deleteFromDB(weather: HistoryWeather){
        getLocalDBRepository.deleteFromDB(weather)
    }
}