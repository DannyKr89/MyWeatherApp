package com.dk.myweatherapp.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dk.myweatherapp.databinding.ItemCityNameBinding
import com.dk.myweatherapp.model.City

class WeatherListAdapter(
    private var onItemViewClick: WeatherListFragment.OnItemViewClick?) :
    RecyclerView.Adapter<WeatherListAdapter.WeatherViewHolder>() {

    private var weatherList = listOf<City>()

    @SuppressLint("NotifyDataSetChanged")
    fun setWeatherList(list:List<City>){
        weatherList = list
        notifyDataSetChanged()
    }

    fun removeListener(){
        onItemViewClick = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding =
            ItemCityNameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    inner class WeatherViewHolder(binding: ItemCityNameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(city: City) {
            with(ItemCityNameBinding.bind(itemView.rootView)) {
                itemCityName.text = city.name
                itemCityName.setOnClickListener {
                    onItemViewClick?.onWeatherClick(city)
                }
            }

        }
    }


}
