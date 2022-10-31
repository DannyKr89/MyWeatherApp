package com.dk.myweatherapp.model.weather_dto


import com.dk.myweatherapp.model.City
import com.google.gson.annotations.SerializedName

data class WeatherDTO(
    var city: City = City(),
    @SerializedName("fact")
    val fact: Fact = Fact(),
    @SerializedName("forecast")
    val forecast: Forecast = Forecast(),
    @SerializedName("info")
    val info: Info = Info()
)