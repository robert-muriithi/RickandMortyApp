package dev.robert.rickandmorty.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.rickandmorty.R
import dev.robert.rickandmorty.adapter.EpisodesAdapter
import dev.robert.rickandmorty.databinding.FragmentCharacterDetailsBinding
import dev.robert.rickandmorty.viewmodel.CharacterDetailsViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {
    private lateinit var binding: FragmentCharacterDetailsBinding
    private val viewModel: CharacterDetailsViewModel by viewModels()
    private val args: CharacterDetailsFragmentArgs by navArgs()
    private val adapter: EpisodesAdapter by lazy { EpisodesAdapter(episodes) }
    private var job: Job? = null
    val episodes: ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        //Hiding navbar
        val navBar = activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navBar.visibility = View.GONE


        val results = args.characterDetails
        val color = args.color



        binding.created.text = results.created
        binding.location.text = results.location.name
        binding.origin.text = results.origin.name
        binding.species.text = results.species
        binding.gender.text = results.gender
        binding.alive.text = results.status
        binding.characterName.text = results.name
        Glide.with(binding.root)
            .load(results.image)
            .into(binding.image)



        if (color != 0) {
            color.let { color ->
                binding.materialCardView.setBackgroundColor(color)
                binding.detailsToolbar.setBackgroundColor(color)
                requireActivity().window.statusBarColor = color
            }
        }

        binding.detailsToolbar.elevation = 0.0F
        (activity as AppCompatActivity).setSupportActionBar(binding.detailsToolbar)
        (activity as AppCompatActivity).supportActionBar!!.title =
            results.name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else {
                    it.toString()
                }
            }
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setHomeButtonEnabled(true)
        binding.detailsToolbar.setNavigationOnClickListener {
            binding.root.findNavController().navigateUp()
        }

        /*setUpAdapter()*/

        return view
    }

    /*private fun setUpAdapter() {
        binding.episodesRecyclerView.adapter = adapter
        job?.cancel()
        adapter.submitList(episodes)
    }*/

}