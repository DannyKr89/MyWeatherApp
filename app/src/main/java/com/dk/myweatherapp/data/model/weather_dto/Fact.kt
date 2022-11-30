package com.dk.myweatherapp.data.model.weather_dto


import com.google.gson.annotations.SerializedName

data class Fact(
    @SerializedName("condition")
    val condition: String = "N/A",
    @SerializedName("feels_like")
    val feelsLike: Int = 0,
    @SerializedName("humidity")
    val humidity: Int = 0,
    @SerializedName("icon")
    val icon: String = "",
    @SerializedName("temp")
    val temp: Int = 0,
    @SerializedName("wind_speed")
    val windSpeed: Double = 0.0 //TODO Добавить скорость ветра
)