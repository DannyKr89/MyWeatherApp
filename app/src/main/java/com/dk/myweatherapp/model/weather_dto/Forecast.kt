package com.dk.myweatherapp.model.weather_dto


import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("parts")
    val parts: List<Part> = listOf(),
    @SerializedName("sunrise")
    val sunrise: String = "",
    @SerializedName("sunset")
    val sunset: String = ""
)