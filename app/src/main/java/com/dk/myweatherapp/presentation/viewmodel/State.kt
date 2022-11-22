package com.dk.myweatherapp.presentation.viewmodel

import com.dk.myweatherapp.data.model.weather_dto.Weather

sealed class State {
    data class SuccessWeather(val weather: Weather) : State()
    data class Error(val error: Throwable): State()
    object Loading: State()
}