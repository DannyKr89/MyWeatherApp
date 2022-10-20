package com.dk.myweatherapp.domain

import com.dk.myweatherapp.model.CitiesLocation
import com.dk.myweatherapp.model.Weather

fun interface RepositoryWeatherSingleCity {
    fun getWeather(weather: Weather): Weather
}
fun interface RepositoryWeatherListCities {
    fun getWeatherList(location: CitiesLocation): List<Weather>
}
fun interface RepositoryNextLoc{
    fun getNextLoc(nextLoc: Boolean):Boolean
}