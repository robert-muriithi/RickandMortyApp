package dev.robert.rickandmorty.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.rickandmorty.R
import dev.robert.rickandmorty.databinding.FragmentFilterBinding
import timber.log.Timber


@AndroidEntryPoint
class FilterFragment : Fragment() {
    private lateinit var binding: FragmentFilterBinding
    var status: String? = null
    var gender: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterBinding.inflate(inflater, container, false)
        val view = binding.root
        val navBar = activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navBar.visibility = View.GONE

        binding.closeBtn.setOnClickListener {
            findNavController().navigate(R.id.action_filterFragment_to_charactersFragment)
        }

        binding.statusChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            /*val chip = group.findViewById<Chip>(checkedIds.first())
            status = chip.text.toString()*/

        }



        return view

    }


}
