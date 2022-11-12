package com.dk.myweatherapp.domain

import com.dk.myweatherapp.model.CitiesLocation
import com.dk.myweatherapp.model.City
import com.dk.myweatherapp.model.weather_dto.WeatherDTO

fun interface RepositoryWeatherListCities {
    fun getWeatherList(location: CitiesLocation): List<City>

}fun interface RepositoryWeather {
    fun getWeather(city: City): WeatherDTO
}
fun interface RepositoryNextLoc{
    fun getNextLoc(nextLoc: Boolean):Boolean
}