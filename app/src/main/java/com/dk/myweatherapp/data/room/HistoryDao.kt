package com.dk.myweatherapp.data.room

import androidx.room.*

@Dao
interface HistoryDao {
    @Query("SELECT * FROM HistoryWeather")
    fun all(): List<HistoryWeather>

    @Query("SELECT * FROM HistoryWeather WHERE city LIKE :city")
    fun getCity(city: String): List<HistoryWeather>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertWeather(weather: HistoryWeather)

    @Update
    fun update(weather: HistoryWeather)

    @Delete
    fun deleteWeather(weather: HistoryWeather)
}