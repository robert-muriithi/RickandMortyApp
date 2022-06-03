package dev.robert.rickandmorty.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.rickandmorty.R
import dev.robert.rickandmorty.databinding.FragmentFilterBinding


@AndroidEntryPoint
class FilterFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentFilterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterBinding.inflate(inflater, container, false)
        val view = binding.root
        val navBar = activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navBar.visibility = View.GONE

        binding.closeBtn.setOnClickListener {
            dismiss()
        }

        return view

    }



}
