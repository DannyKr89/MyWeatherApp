package com.dk.myweatherapp.viewmodel

import com.dk.myweatherapp.model.City
import com.dk.myweatherapp.model.weather_dto.Weather

sealed class State {
    data class SuccessWeatherList(val weatherList: List<City>) : State()
    data class SuccessWeather(val weather: Weather) : State()
    data class Error(val error: Throwable): State()
    object Loading: State()
}