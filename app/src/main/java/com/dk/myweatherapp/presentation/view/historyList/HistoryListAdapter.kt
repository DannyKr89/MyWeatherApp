package com.dk.myweatherapp.presentation.view.historyList

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dk.myweatherapp.data.common.WeatherCondition
import com.dk.myweatherapp.data.common.translateWeatherCondition
import com.dk.myweatherapp.data.room.HistoryWeather
import com.dk.myweatherapp.databinding.ItemHistoryCityBinding
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

class HistoryListAdapter() : ListAdapter<HistoryWeather,HistoryListAdapter.HistoryListViewHolder>(HistoryWeatherCallback()) {

    inner class HistoryListViewHolder(val binding: ItemHistoryCityBinding) :
        ViewHolder(binding.root) {

        fun bind(weather: HistoryWeather) {
            with(binding) {
                cityName.text = weather.city
                temperature.text = weather.temp.toString()
                condition.text = this.root.context.getString(
                    translateWeatherCondition(
                        WeatherCondition.getWeatherCondition(weather.condition)
                    )
                )
                GlideToVectorYou.init().with(imageCondition.context)
                    .load(Uri.parse(weather.image), imageCondition);
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryListViewHolder {
        val binding =
            ItemHistoryCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}