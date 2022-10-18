package com.dk.myweatherapp.model

data class Weather(
    val city: City = getDefaultCity(),
    val temperature: Int = 5,
    val feelsLike: Int = 7
    )
