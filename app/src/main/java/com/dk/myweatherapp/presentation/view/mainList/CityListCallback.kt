package com.dk.myweatherapp.presentation.view.mainList

import androidx.recyclerview.widget.DiffUtil
import com.dk.myweatherapp.data.model.City

class CityListCallback: DiffUtil.ItemCallback<City>() {
    override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem == newItem
    }
}