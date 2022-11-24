package com.dk.myweatherapp.presentation.view.historyList

import androidx.recyclerview.widget.DiffUtil
import com.dk.myweatherapp.data.room.HistoryWeather

class HistoryWeatherCallback: DiffUtil.ItemCallback<HistoryWeather>() {
    override fun areItemsTheSame(oldItem: HistoryWeather, newItem: HistoryWeather): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: HistoryWeather, newItem: HistoryWeather): Boolean {
        return oldItem == newItem
    }
}