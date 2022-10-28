package com.dk.myweatherapp.model.weather_dto


import com.dk.myweatherapp.model.City
import com.google.gson.annotations.SerializedName

data class WeatherDTO(
    var city: City,
    @SerializedName("fact")
    val fact: Fact,
    @SerializedName("forecast")
    val forecast: Forecast,
    @SerializedName("info")
    val info: Info
)