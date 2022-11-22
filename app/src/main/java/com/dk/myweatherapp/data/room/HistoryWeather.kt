package com.dk.myweatherapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryWeather(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var city: String,
    val temp: Int,
    val condition: String,
    val image: String
)
