package com.dk.myweatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dk.myweatherapp.R
import com.dk.myweatherapp.databinding.FragmentWeatherListBinding
import com.dk.myweatherapp.model.Weather
import com.dk.myweatherapp.viewmodel.State
import com.dk.myweatherapp.viewmodel.WeatherViewModel
import com.google.android.material.snackbar.Snackbar

class WeatherListFragment : Fragment() {
    private var _binding: FragmentWeatherListBinding? = null
    private val binding: FragmentWeatherListBinding
        get() {
            return _binding!!
        }
    private val viewModel: WeatherViewModel by activityViewModels()
    private lateinit var adapter: WeatherListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.getRussiansCitiesList()
        }

        viewModel.getWeatherState().observe(viewLifecycleOwner) {
            when (it) {
                is State.Error -> {
                    with(binding) {
                        progress.visibility = View.GONE
                        fabChangeList.visibility = View.GONE
                    }

                    Snackbar.make(view,getString(R.string.loading_error),Snackbar.LENGTH_INDEFINITE)
                        .setAction(getString(R.string.repeat), View.OnClickListener {
                            if (viewModel.getNextLocation().value!!) {
                                viewModel.getRussiansCitiesList()
                            } else {
                                viewModel.getWorldCitiesList()
                            }
                        })
                        .show()
                }
                is State.Loading -> {
                    with(binding) {
                        progress.visibility = View.VISIBLE
                        rvWeatherList.visibility = View.GONE
                        fabChangeList.visibility = View.GONE
                    }
                }
                is State.Success -> {
                    with(binding) {
                        progress.visibility = View.GONE
                        rvWeatherList.visibility = View.VISIBLE
                        fabChangeList.visibility = View.VISIBLE
                    }
                    renderList(it.weatherList)
                }
            }
        }

        viewModel.getNextLocation().observe(viewLifecycleOwner) {
            if (it) {
                binding.fabChangeList.setImageResource(R.drawable.russia)
            } else {
                binding.fabChangeList.setImageResource(R.drawable.earth)
            }
        }


        binding.fabChangeList.setOnClickListener {
            viewModel.changeLocation(viewModel.getNextLocation().value!!)

            if (viewModel.getNextLocation().value!!) {
                viewModel.getRussiansCitiesList()
            } else {
                viewModel.getWorldCitiesList()
            }
        }

    }

    private fun renderList(weathers: List<Weather>) {
        binding.rvWeatherList.adapter = WeatherListAdapter(object : OnItemViewClick {
            override fun onWeatherClick(weather: Weather) {
                activity?.apply {
                    supportFragmentManager.beginTransaction()
                        .add(R.id.mainContainer, DetailWeatherFragment.newInstance(Bundle().apply {
                            putParcelable(
                                DetailWeatherFragment.WEATHER, weather
                            )
                        })).addToBackStack("").commit()
                }
            }
        }).also {
            it.setWeatherList(weathers)
        }
    }

    interface OnItemViewClick {
        fun onWeatherClick(weather: Weather)
    }

    override fun onDestroyView() {
        _binding = null
        adapter.removeListener()
        super.onDestroyView()
    }


    companion object {
        @JvmStatic
        fun newInstance() = WeatherListFragment()
    }
}
