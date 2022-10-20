package com.dk.myweatherapp.domain

import com.dk.myweatherapp.model.Weather

interface Repository {
    fun getWeather(weather: Weather): Weather
}