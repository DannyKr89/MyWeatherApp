package com.dk.myweatherapp.presentation.view.historyList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dk.myweatherapp.data.room.HistoryWeather
import com.dk.myweatherapp.databinding.FragmentHistoryBinding
import com.dk.myweatherapp.presentation.viewmodel.HistoryListViewModel

class HistoryListFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val viewModel: HistoryListViewModel by activityViewModels()
    private val binding get() = _binding!!
    private var adapter = HistoryListAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvHistoryList.adapter = adapter
        viewModel.getAllHistory().observe(viewLifecycleOwner) {
            renderList(it)
        }
        viewModel.getAllHistory()
    }

    private fun renderList(list: List<HistoryWeather>) {
        adapter.submitList(list.reversed())
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}