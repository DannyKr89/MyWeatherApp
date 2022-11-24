package com.dk.myweatherapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dk.myweatherapp.App.Companion.getHistoryWeatherDB
import com.dk.myweatherapp.data.repository.LocalRepositoryImpl
import com.dk.myweatherapp.data.room.HistoryWeather
import com.dk.myweatherapp.domain.GetLocalDBInteractor
import com.dk.myweatherapp.domain.GetLocalDBRepository

class HistoryListViewModel(
    private val repository: GetLocalDBRepository = LocalRepositoryImpl(getHistoryWeatherDB()),
    private val getLocalDBInteractor: GetLocalDBInteractor = GetLocalDBInteractor(repository)
) : ViewModel() {

    fun getAllHistory(): LiveData<List<HistoryWeather>> {
        return getLocalDBInteractor.getAllHistory()
    }
}