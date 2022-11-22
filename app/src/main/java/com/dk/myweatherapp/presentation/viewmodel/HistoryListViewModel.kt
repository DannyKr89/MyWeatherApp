package com.dk.myweatherapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dk.myweatherapp.App.Companion.getHistoryWeatherDB
import com.dk.myweatherapp.data.repository.LocalRepositoryImpl
import com.dk.myweatherapp.data.room.HistoryWeather
import com.dk.myweatherapp.domain.LocalRepository

class HistoryListViewModel(
    val historyListLiveData: MutableLiveData<List<HistoryWeather>> = MutableLiveData(),
    private val localRepository: LocalRepository = LocalRepositoryImpl(getHistoryWeatherDB())
) : ViewModel() {

    fun getAllHistory() {
        Thread{
            historyListLiveData.postValue(localRepository.getAllHistory())
        }.start()


    }
}