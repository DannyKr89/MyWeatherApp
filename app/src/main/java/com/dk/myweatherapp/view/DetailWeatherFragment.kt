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
import com.dk.myweatherapp.model.Weather
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
//        arguments?.let {
//            it.getParcelable<Weather>(WEATHER)?.let { weather ->
//                Thread {
//                    try {
//                        val weatherDTO = requestWeatherDTO(weather)
//                        activity?.runOnUiThread {
//                            bindWeather(weather.city.name, weatherDTO)
//                        }
//                    } catch (e: SocketTimeoutException) {
//                        activity?.runOnUiThread {
//                            Toast.makeText(
//                                context,
//                                "Вышло время ожидания ответа",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }
//                        Log.e("ERROR", e.toString())
//                        activity?.supportFragmentManager?.popBackStack()
//
//                    }
//                }.start()
//
//            }
//        }
        val weather = arguments?.getParcelable<Weather>(WEATHER)!!

        viewModel.getWeatherState().observe(viewLifecycleOwner) {
            when (it) {
                is State.Error -> {
                    Toast.makeText(
                        context, "Вышло время ожидания ответа", Toast.LENGTH_LONG
                    ).show()
                }
                State.Loading -> {
                    with(binding) {
                        progressbarDetail.visibility = View.VISIBLE
                        listViewWeather.visibility = View.GONE

                    }

                }
                is State.SuccessWeather -> {
                    with(binding) {
                        progressbarDetail.visibility = View.GONE
                        listViewWeather.visibility = View.VISIBLE

                    }

                    bindWeather(weather.city.name, it.weather)
                }
                is State.SuccessWeatherList -> {}
            }
        }
        viewModel.getWeatherRequestState(weather)


    }

    private fun bindWeather(city: String, weather: WeatherDTO) {
        with(binding) {
            cityName.text = city


            coordinates.text = buildString {
                append(weather.info.lat)
                append(" / ")
                append(weather.info.lon)
            }
            condition.text = getString(getLocaleWeather(weather.fact.condition))

            temperature.text = weather.fact.temp.toString()
            feelsLike.text = weather.fact.feelsLike.toString()

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

        const val WEATHER = "weather"

        @JvmStatic
        fun newInstance(bundle: Bundle): DetailWeatherFragment = DetailWeatherFragment().apply {
            arguments = bundle
            }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}