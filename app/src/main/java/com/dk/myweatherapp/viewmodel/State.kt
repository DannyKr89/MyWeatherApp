package com.dk.myweatherapp.viewmodel

import com.dk.myweatherapp.model.Weather

sealed class State {
    data class Success(val weatherList: List<Weather>) : State()
    data class Error(val error: Throwable): State()
    object Loading: State()
}