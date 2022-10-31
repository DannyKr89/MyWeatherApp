package com.dk.myweatherapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    val name:String = "N/A",
    val lat: Double = 0.0,
    val lon: Double = 0.0
) : Parcelable
