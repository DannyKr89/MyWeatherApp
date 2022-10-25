package com.dk.myweatherapp.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dk.myweatherapp.databinding.ItemCityNameBinding
import com.dk.myweatherapp.model.Weather

class WeatherListAdapter(
    private var onItemViewClick: WeatherListFragment.OnItemViewClick?) :
    RecyclerView.Adapter<WeatherListAdapter.WeatherViewHolder>() {

    private var weatherList = listOf<Weather>()

    @SuppressLint("NotifyDataSetChanged")
    fun setWeatherList(list:List<Weather>){
        weatherList = list
        notifyDataSetChanged()
    }

    fun removeListener(){
        onItemViewClick = null
    }

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

    inner class WeatherViewHolder(binding: ItemCityNameBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(weather: Weather) {
            val binding = ItemCityNameBinding.bind(itemView.rootView)
            binding.itemCityName.text = weather.city.name
            binding.itemCityName.setOnClickListener {
                onItemViewClick?.onWeatherClick(weather)
            }
        }
    }


}
