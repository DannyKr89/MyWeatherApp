package com.dk.myweatherapp.domain

import com.dk.myweatherapp.model.CitiesLocation
import com.dk.myweatherapp.model.City
import com.dk.myweatherapp.model.weather_dto.Weather
import retrofit2.Callback

fun interface RepositoryWeatherListCities {
    fun getWeatherList(location: CitiesLocation): List<City>

}fun interface RepositoryWeather {
    fun getWeatherFromAPI(city: City, callback: Callback<Weather>)
}
fun interface RepositoryNextLoc{
    fun getNextLoc(nextLoc: Boolean):Boolean
}