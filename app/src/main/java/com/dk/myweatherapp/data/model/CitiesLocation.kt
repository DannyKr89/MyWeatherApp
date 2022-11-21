package com.dk.myweatherapp.data.model

sealed class CitiesLocation {
    object RussianCities : CitiesLocation()
    object WorldCities : CitiesLocation()
}