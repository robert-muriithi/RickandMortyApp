package dev.robert.rickandmorty.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.rickandmorty.R
import dev.robert.rickandmorty.databinding.FragmentEpisodesBinding

@AndroidEntryPoint
class EpisodesFragment : Fragment() {
private lateinit var binding: FragmentEpisodesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEpisodesBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.episodesToolbar.elevation = 0.0F
        (activity as AppCompatActivity).setSupportActionBar(binding.episodesToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setHomeButtonEnabled(true)
        binding.episodesToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }


        return view
    }

}