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
        val weather = arguments?.getParcelable<Weather>(BUNDLE)
        if (weather != null){
            binding.cityName.text = "Город: ${weather.city.name}"
            binding.coordinates.text = "Координаты: ${weather.city.lat} / ${weather.city.lon}"
            binding.temperature.text = "Температура: ${weather.temperature}"
            binding.feelsLike.text = "Чувствуется как: ${weather.feelsLike}"
        }
    }

    companion object {

        const val BUNDLE = "weather"

        @JvmStatic
        fun newInstance(bundle: Bundle): DetailWeatherFragment {
            val fragment = DetailWeatherFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}