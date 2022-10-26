package com.dk.myweatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dk.myweatherapp.databinding.FragmentDetailWeatherBinding
import com.dk.myweatherapp.model.Weather

class DetailWeatherFragment : Fragment() {
    private var _binding: FragmentDetailWeatherBinding? = null
    private val binding: FragmentDetailWeatherBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            it.getParcelable<Weather>(WEATHER)?.let {
                with(binding) {
                    cityName.text = "Город: ${it.city.name}"
                    coordinates.text = "Координаты: ${it.city.lat} / ${it.city.lon}"
                    temperature.text = "Температура: ${it.temperature}"
                    feelsLike.text = "Чувствуется как: ${it.feelsLike}"
                }
            }
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