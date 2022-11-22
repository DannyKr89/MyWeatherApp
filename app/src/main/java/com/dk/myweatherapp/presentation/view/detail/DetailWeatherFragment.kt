package com.dk.myweatherapp.presentation.view.detail

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dk.myweatherapp.data.CITY
import com.dk.myweatherapp.data.ICON_URL
import com.dk.myweatherapp.data.common.WeatherCondition
import com.dk.myweatherapp.data.common.translateWeatherCondition
import com.dk.myweatherapp.data.model.City
import com.dk.myweatherapp.data.model.weather_dto.Weather
import com.dk.myweatherapp.databinding.FragmentDetailWeatherBinding
import com.dk.myweatherapp.presentation.viewmodel.State
import com.dk.myweatherapp.presentation.viewmodel.WeatherDetailViewModel
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou


class DetailWeatherFragment : Fragment() {
    private var _binding: FragmentDetailWeatherBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WeatherDetailViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val city = arguments?.getParcelable<City>(CITY)!!

        viewModel.getWeatherState().observe(viewLifecycleOwner) {
            when (it) {
                is State.Error -> {
                    hideProgressbar()
                    Toast.makeText(
                        context, it.error.message, Toast.LENGTH_LONG
                    ).show()
                }
                State.Loading -> {
                    showProgressbar()
                }
                is State.SuccessWeather -> {
                    hideProgressbar()
                    bindWeather(it.weather)
                }
            }
        }
        viewModel.getWeatherRequestState(city)
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

    private fun bindWeather(weather: Weather) {
        with(binding) {

            condition.text =
                getString(translateWeatherCondition(WeatherCondition.getWeatherCondition(weather.fact.condition)))

            GlideToVectorYou.justLoadImage(
                activity,
                Uri.parse(ICON_URL + weather.fact.icon + ".svg"),
                weatherIcon
            )

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
                    append(
                        getString(
                            translateWeatherCondition(
                                WeatherCondition.getWeatherCondition(
                                    weather.forecast.parts[i].partName
                                )
                            )
                        )
                    )
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}