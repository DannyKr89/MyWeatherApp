package com.dk.myweatherapp.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class WeatherBR : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Связь изменилась", Toast.LENGTH_LONG).show()
    }
}