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

        viewModel.getWeatherList().observe(
            viewLifecycleOwner
        ) {
            renderList(it)
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
        adapter = WeatherListAdapter(object : OnItemViewClick {
            override fun onWeatherClick(weather: Weather) {
                val manager = activity?.supportFragmentManager
                if (manager != null){
                    val bundle = Bundle()
                    bundle.putParcelable(DetailWeatherFragment.BUNDLE,weather)
                    manager.beginTransaction()
                        .add(R.id.mainContainer, DetailWeatherFragment.newInstance(bundle))
                        .addToBackStack("")
                        .commit()
                }
            }

        })
        adapter.setWeatherList(weathers)
        binding.rvWeatherList.adapter = adapter
    }

    interface OnItemViewClick {
        fun onWeatherClick(weather: Weather)
    }

    override fun onDestroy() {
        _binding = null
        adapter.removeListener()
        super.onDestroy()
    }


    companion object {
        @JvmStatic
        fun newInstance() = WeatherListFragment()
    }
}
