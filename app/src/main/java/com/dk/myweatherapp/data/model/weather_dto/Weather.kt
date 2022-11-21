package com.dk.myweatherapp.data.model.weather_dto


import com.dk.myweatherapp.data.model.City
import com.google.gson.annotations.SerializedName

data class Weather(
    var city: City = City(),
    @SerializedName("fact")
    val fact: Fact = Fact(),
    @SerializedName("forecast")
    val forecast: Forecast = Forecast(),
    @SerializedName("info")
    val info: Info = Info()
)