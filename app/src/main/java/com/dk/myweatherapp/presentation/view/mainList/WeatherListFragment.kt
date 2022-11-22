package com.dk.myweatherapp.presentation.view.mainList

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.dk.myweatherapp.R
import com.dk.myweatherapp.data.CITY
import com.dk.myweatherapp.data.LIST_KEY
import com.dk.myweatherapp.data.model.City
import com.dk.myweatherapp.databinding.FragmentWeatherListBinding
import com.dk.myweatherapp.presentation.viewmodel.WeatherListViewModel

class WeatherListFragment : Fragment() {
    private var _binding: FragmentWeatherListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WeatherListViewModel by activityViewModels()
    private lateinit var adapter: WeatherListAdapter
    private lateinit var sharedPref: SharedPreferences
    private var isDataSetWorld = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadLastList()

        init()
    }

    private fun loadLastList() {
        sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)!!
        isDataSetWorld = sharedPref.getBoolean(LIST_KEY, false)
        changeList()
    }

    private fun init() {
        viewModel.getWeatherListState().observe(viewLifecycleOwner) {
            renderList(it)
        }


        with(binding) {

            fabChangeList.setOnClickListener {
                isDataSetWorld = !isDataSetWorld
                changeList()
            }

            btnSearch.setOnClickListener {
                if (latitude.text.isNotEmpty() && longitude.text.isNotEmpty()) {
                    val city = City(
                        "Свои координаты",
                        latitude.text.toString().toDouble(),
                        longitude.text.toString().toDouble()
                    )
                    findNavController().navigate(R.id.action_weatherListFragment_to_detailWeatherFragment,
                        Bundle().apply {
                            putParcelable(
                                CITY, city
                            )
                            putString("cityName", city.name)
                        })
                } else {
                    Toast.makeText(
                        super.getContext(),
                        "Введите широту и долготу",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun changeList() {
        if (isDataSetWorld) {
            binding.fabChangeList.setImageResource(R.drawable.earth)
        } else {
            binding.fabChangeList.setImageResource(R.drawable.russia)
        }
        viewModel.getWeatherListRequestState(isDataSetWorld)
        sharedPref.edit().putBoolean(LIST_KEY, isDataSetWorld).apply()
    }

    private fun renderList(weathers: List<City>) {
        adapter = WeatherListAdapter(object : OnItemViewClick {
            override fun onWeatherClick(city: City) {
                findNavController().navigate(
                    R.id.action_weatherListFragment_to_detailWeatherFragment,
                    Bundle().apply {
                        putParcelable(
                            CITY, city
                        )
                        putString("cityName", city.name)
                    })
            }
        }).also {
            it.setWeatherList(weathers)
        }
        binding.rvWeatherList.adapter = adapter
    }

    interface OnItemViewClick {
        fun onWeatherClick(city: City)
    }

    override fun onDestroyView() {
        _binding = null
        adapter.removeListener()
        super.onDestroyView()
    }
}

