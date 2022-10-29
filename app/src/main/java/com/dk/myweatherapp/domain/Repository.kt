package com.dk.myweatherapp.domain

import com.dk.myweatherapp.model.CitiesLocation
import com.dk.myweatherapp.model.Weather
import com.dk.myweatherapp.model.weather_dto.WeatherDTO

fun interface RepositoryWeatherListCities {
    fun getWeatherList(location: CitiesLocation): List<Weather>

}fun interface RepositoryWeather {
    fun getWeather(weather: Weather): WeatherDTO
}
fun interface RepositoryNextLoc{
    fun getNextLoc(nextLoc: Boolean):Boolean
}