package dev.robert.rickandmorty.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import dev.robert.rickandmorty.R
import dev.robert.rickandmorty.adapter.CharactersPagingAdapter
import dev.robert.rickandmorty.databinding.FragmentCharactersListBinding
import dev.robert.rickandmorty.viewmodel.CharactersMainViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharactersFragment : Fragment() {
    private lateinit var binding: FragmentCharactersListBinding
    private val viewModel: CharactersMainViewModel by viewModels()
    private val adapter: CharactersPagingAdapter by lazy { CharactersPagingAdapter() }
    private var job: Job? = null
    private var hasUserSearched = false
    private var hasInitiatedInitialCall = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCharactersListBinding.inflate(inflater, container, false)
        val view = binding.root
        setAdapter()
        setRefresh()

        return view
    }

    private fun setRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            getCharacter(null, true)
        }
        binding.inputLayout.apply {
            editText?.text = null
            isFocusable = false
        }
    }

    private fun getCharacter(searchString: String?, shouldPerformSearch: Boolean) {
        job?.cancel()
        job = lifecycleScope.launch {
            if (shouldPerformSearch) {
                adapter.submitData(PagingData.empty())
            }
            viewModel.getCharacters(searchString).collectLatest {
                adapter.submitData(it)
            }
        }

    }

    private fun setAdapter() {

        binding.apply {
            recyclerView.adapter = adapter
            adapter.addLoadStateListener { }
            getCharacter(null, false)
            recyclerView.addOnScrollListener(object  : RecyclerView.OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val scrollPosition = (recyclerView
                        .layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()

                    if (scrollPosition != null){
                        if (scrollPosition >= 1){
                            //do something
                        }
                        else {
                            //do something
                        }
                    }
                }
            })
        }

    }

    fun performSearch(searchString: String) {
        if (searchString.isNotEmpty()) {
            getCharacter(searchString, true)
        }
    }

}