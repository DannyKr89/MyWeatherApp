package com.dk.myweatherapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(HistoryWeather::class), version = 2, exportSchema = false)
abstract class HistoryWeatherDB : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}