package com.dk.myweatherapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dk.myweatherapp.databinding.FragmentWeatherMainBinding
import com.dk.myweatherapp.model.City
import com.dk.myweatherapp.model.Weather

class WeatherMainFragment : Fragment() {

    lateinit var binding: FragmentWeatherMainBinding
    lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherMainBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = WeatherMainFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        viewModel.getData().observe(viewLifecycleOwner, object : Observer<Weather> {
            override fun onChanged(t: Weather) {
                with(binding){
                    cityName.text = t.city.name
                    coordinates.text = "${t.city.lat} / ${t.city.lon}"
                    temperature.text = t.temperature.toString()
                    feelsLike.text = t.feelsLike.toString()
                }

            }
        })
        viewModel.request(Weather())

        binding.btn.setOnClickListener {
            val weather = Weather(City("Kirov",156.21112,210.25466),50,10)
            viewModel.request(weather)
        }
    }

}