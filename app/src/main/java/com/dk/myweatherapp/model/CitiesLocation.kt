package com.dk.myweatherapp.model

sealed class CitiesLocation {
    object RussianCities : CitiesLocation()
    object WorldCities : CitiesLocation()
}