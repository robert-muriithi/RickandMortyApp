package dev.robert.rickandmorty.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.rickandmorty.R
import dev.robert.rickandmorty.adapter.LocationPagingAdapter
import dev.robert.rickandmorty.databinding.FragmentCharacterLocationBinding
import dev.robert.rickandmorty.viewmodel.CharacterLocationViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterLocationFragment : Fragment() {
    private lateinit var binding: FragmentCharacterLocationBinding
    private val adapter: LocationPagingAdapter by lazy {
        LocationPagingAdapter()
    }
    private val viewModel: CharacterLocationViewModel by viewModels()

    var job: Job? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterLocationBinding.inflate(inflater, container, false)
        val view = binding.root
        requireActivity().window.statusBarColor = resources.getColor(R.color.black)
        binding.toolbar.elevation = 0.0F
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setHomeButtonEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        setupAdapter()
        sepUpRefresh()
        return view
    }

    private fun sepUpRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            getLocations()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun setupAdapter() {
        binding.locationRecyclerView.adapter = adapter
        // binding.locProgressBar.isVisible = true
        adapter.addLoadStateListener { loadState ->
            binding.locProgressBar.isVisible =
                loadState.refresh is LoadState.Loading && adapter.snapshot().isEmpty()
        }
        getLocations()
    }

    private fun getLocations() {
        job?.cancel()
        job = lifecycleScope.launch {
            viewModel.getLocationList().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().window.statusBarColor = resources.getColor(R.color.black)
    }

}