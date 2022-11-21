package com.dk.myweatherapp.data.model.weather_dto


import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("lat")
    val lat: Double = 0.0,
    @SerializedName("lon")
    val lon: Double = 0.0,
    @SerializedName("url")
    val url: String = ""
)