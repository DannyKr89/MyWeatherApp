package com.dk.myweatherapp.model.weather_dto


import android.os.Parcelable
import com.dk.myweatherapp.model.City
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(
    var city: City = City(),
    @SerializedName("fact")
    val fact: Fact = Fact(),
    @SerializedName("forecast")
    val forecast: Forecast = Forecast(),
    @SerializedName("info")
    val info: Info = Info()
) : Parcelable