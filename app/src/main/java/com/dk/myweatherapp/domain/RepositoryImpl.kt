package com.dk.myweatherapp.domain

import com.dk.myweatherapp.model.Weather

class RepositoryImpl: Repository {
    override fun getWeather(weather: Weather): Weather {
        return weather
    }
}