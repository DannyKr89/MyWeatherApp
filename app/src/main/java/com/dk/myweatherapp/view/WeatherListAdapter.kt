package com.dk.myweatherapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dk.myweatherapp.databinding.ItemCityNameBinding
import com.dk.myweatherapp.model.Weather

class WeatherListAdapter(private val weatherList: List<Weather>) :
    RecyclerView.Adapter<WeatherListAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = ItemCityNameBinding.inflate(LayoutInflater.from(parent.context))
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    class WeatherViewHolder(binding: ItemCityNameBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(weather: Weather) {
            val binding = ItemCityNameBinding.bind(itemView.rootView)
            binding.itemCityName.text = weather.city.name
        }
    }
}