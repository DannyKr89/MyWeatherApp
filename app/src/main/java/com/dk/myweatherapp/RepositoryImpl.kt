package com.dk.myweatherapp

import com.dk.myweatherapp.model.Weather

class RepositoryImpl: Repository {
    override fun getWeather(weather: Weather): Weather {
        return weather
    }
}