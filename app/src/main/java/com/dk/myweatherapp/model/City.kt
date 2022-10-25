package com.dk.myweatherapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    val name:String,
    val lat: Double,
    val lon: Double
) : Parcelable

fun getDefaultCity() = City("Москва", 55.833333, 37.616667)
