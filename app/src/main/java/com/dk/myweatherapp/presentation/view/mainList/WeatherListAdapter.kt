package com.dk.myweatherapp.presentation.view.mainList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dk.myweatherapp.data.model.City
import com.dk.myweatherapp.databinding.ItemCityNameBinding

class WeatherListAdapter() :
    ListAdapter<City, WeatherListAdapter.WeatherViewHolder>(CityListCallback()) {

    var listener: ((City) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding =
            ItemCityNameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class WeatherViewHolder(binding: ItemCityNameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(city: City) {
            with(ItemCityNameBinding.bind(itemView.rootView)) {
                itemCityName.text = city.name
                itemCityName.setOnClickListener {
                    listener?.invoke(city)
                }
            }
        }
    }
}
