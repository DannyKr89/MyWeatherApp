package com.dk.myweatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dk.myweatherapp.databinding.FragmentDetailWeatherBinding
import com.dk.myweatherapp.domain.getLocaleWeather
import com.dk.myweatherapp.domain.getWeatherConditionIcon
import com.dk.myweatherapp.model.City
import com.dk.myweatherapp.model.weather_dto.WeatherDTO
import com.dk.myweatherapp.viewmodel.State
import com.dk.myweatherapp.viewmodel.WeatherViewModel

class DetailWeatherFragment : Fragment() {
    private var _binding: FragmentDetailWeatherBinding? = null
    private val binding: FragmentDetailWeatherBinding
        get() {
            return _binding!!
        }
    private val viewModel: WeatherViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weather = arguments?.getParcelable<City>(CITY)!!



        viewModel.getWeatherState().observe(viewLifecycleOwner) {
            when (it) {
                is State.Error -> {
                    hideProgressbar()
                    Toast.makeText(
                        context, "Ошибка", Toast.LENGTH_LONG
                    ).show()
                }
                State.Loading -> {
                    showProgressbar()
                }
                is State.SuccessWeather -> {
                    hideProgressbar()
                    bindWeather(it.weather)
                }
                is State.SuccessWeatherList -> {}
            }
        }
        viewModel.getWeatherRequestState(weather)


    }

    private fun hideProgressbar() {
        with(binding) {
            progressbarDetail.visibility = View.GONE
            listViewWeather.visibility = View.VISIBLE
        }
    }

    private fun showProgressbar() {
        with(binding) {
            progressbarDetail.visibility = View.VISIBLE
            listViewWeather.visibility = View.GONE
        }
    }

    private fun bindWeather(weather: WeatherDTO) {
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

    companion object {
        const val CITY = "city"
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}