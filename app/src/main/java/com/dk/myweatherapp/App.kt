package com.dk.myweatherapp

import android.app.Application
import androidx.room.Room
import com.dk.myweatherapp.data.room.HistoryDao
import com.dk.myweatherapp.data.room.HistoryWeatherDB

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private var db: HistoryWeatherDB? = null
        private const val DB_NAME = "History.db"

        fun getHistoryWeatherDB(): HistoryDao {
            if (db == null) {
                synchronized(HistoryWeatherDB::class.java) {
                    if (db == null) {
                        if (appInstance == null){
                            throw java.lang.IllegalStateException("App is null while creating DB")
                        }

                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            HistoryWeatherDB::class.java,
                            DB_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return db!!.historyDao()
        }
    }
}