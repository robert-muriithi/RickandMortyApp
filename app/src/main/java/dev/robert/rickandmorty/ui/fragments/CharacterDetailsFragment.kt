package dev.robert.rickandmorty.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.rickandmorty.R
import dev.robert.rickandmorty.databinding.FragmentCharacterLocationBinding

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {
    private lateinit var binding: FragmentCharacterLocationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCharacterLocationBinding.inflate(inflater, container, false)
        val view = binding.root


        return view
    }

}