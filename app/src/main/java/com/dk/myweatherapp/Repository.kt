package com.dk.myweatherapp

import com.dk.myweatherapp.model.Weather

interface Repository {
    fun getWeather(weather: Weather): Weather
}