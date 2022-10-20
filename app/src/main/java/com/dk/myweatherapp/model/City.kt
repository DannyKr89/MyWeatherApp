package com.dk.myweatherapp.model

data class City(
    val name:String,
    val lat: Double,
    val lon: Double
)

fun getDefaultCity() = City("Москва", 55.833333, 37.616667)
