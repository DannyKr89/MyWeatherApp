package com.dk.myweatherapp.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dk.myweatherapp.databinding.FragmentWeatherMainBinding
import com.dk.myweatherapp.model.City
import com.dk.myweatherapp.model.Weather
import java.util.*

class WeatherMainFragment : Fragment() {
    private var _binding: FragmentWeatherMainBinding? = null
    private val binding: FragmentWeatherMainBinding
        get() {
            return _binding!!
        }
    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherMainBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = WeatherMainFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weathers = listOf<Weather>(
            Weather(),
            Weather(City("Киров", 156.21112, 967.25466), 50, 23),
            Weather(City("Владивосток", 324.21112, 234.25466), 24, 58),
            Weather(City("Санкт-Петербург", 346.21112, 753.25466), 12, 54),
            Weather(City("Королев", 246.21112, 345.25466), 34, 30),
            Weather(City("Киржач", 856.21112, 574.25466), 21, 12),
            Weather(City("Нижний-Новгород", 266.21112, 367.25466), 12, 35)
        )

        viewModel.getWeather().observe(
            viewLifecycleOwner
        ) {
            bindWeather(it)
        }
        binding.btn.setOnClickListener {
            val random = Random().nextInt(weathers.size)
            viewModel.request(weathers[random])
        }
        viewModel.request(Weather())
    }


    private fun bindWeather(weather: Weather) {
        binding
        with(binding) {
            cityName.text = "Город: ${weather.city.name}"
            coordinates.text = "Координаты: ${weather.city.lat} / ${weather.city.lon}"
            temperature.text = "Температура: ${weather.temperature.toString()}"
            feelsLike.text = "Чувствуется как: ${weather.feelsLike.toString()}"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}