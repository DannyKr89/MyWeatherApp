package com.dk.myweatherapp.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.dk.myweatherapp.databinding.FragmentDetailWeatherBinding
import com.dk.myweatherapp.domain.getLocaleWeather
import com.dk.myweatherapp.domain.getWeatherConditionIcon
import com.dk.myweatherapp.model.City
import com.dk.myweatherapp.model.weather_dto.Weather
import com.dk.myweatherapp.services.DetailWeatherService

class DetailWeatherFragment : Fragment() {
    private var _binding: FragmentDetailWeatherBinding? = null
    private val binding: FragmentDetailWeatherBinding
        get() {
            return _binding!!
        }
    private lateinit var cityBundle: City
    private val loadWeatherReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getStringExtra(LOAD_RESULT)) {
                ERROR -> {
                    Toast.makeText(requireContext(), "Ошибка загрузки!", Toast.LENGTH_SHORT).show()
                    val weather = intent.getParcelableExtra<Weather>(WEATHER)!!
                    bindWeather(weather)
                }
                SUCCESS -> {
                    val weather = intent.getParcelableExtra<Weather>(WEATHER)!!
                    bindWeather(weather)
                }
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(loadWeatherReceiver, IntentFilter(WEATHER))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cityBundle = arguments?.getParcelable(CITY)!!
        getWeather()
    }

    private fun getWeather() {
        showProgressbar()
        context?.let {
            it.startService(Intent(it, DetailWeatherService::class.java).apply {
                putExtra(CITY, cityBundle)
            })
        }
    }

    private fun bindWeather(weather: Weather) {
        hideProgressbar()
        with(binding) {
            condition.text = getString(getLocaleWeather(weather.fact.condition))
            weatherIcon.setImageResource(getWeatherConditionIcon(weather.fact.condition))
            temperature.text = buildString {
                append(weather.fact.temp.toString())
                append("°C")
            }
            feelsLike.text = buildString {
                append(weather.fact.feelsLike.toString())
                append("°C")
            }
            minMaxTemp.text = buildString {
                for (i in weather.forecast.parts.indices) {
                    append(getString(getLocaleWeather(weather.forecast.parts[i].partName)))
                    append(" ")
                    append(weather.forecast.parts[i].tempMin)
                    append(" / ")
                    append(weather.forecast.parts[i].tempMax)
                    if (i != weather.forecast.parts.lastIndex) append("\n")
                }
            }
            humidity.text = buildString {
                append(weather.fact.humidity.toString())
                append("%")
            }
            sunrise.text = weather.forecast.sunrise
            sunset.text = weather.forecast.sunset

        }
    }

    private fun showProgressbar() {
        with(binding) {
            progressbarDetail.visibility = View.VISIBLE
            listViewWeather.visibility = View.GONE
        }
    }

    private fun hideProgressbar() {
        with(binding) {
            progressbarDetail.visibility = View.GONE
            listViewWeather.visibility = View.VISIBLE
        }
    }

    companion object {
        const val CITY = "city"
        const val WEATHER = "weather"
        const val LOAD_RESULT = "load result"
        const val ERROR = "error"
        const val SUCCESS = "success"
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadWeatherReceiver)
        }
        super.onDestroy()
    }
}
