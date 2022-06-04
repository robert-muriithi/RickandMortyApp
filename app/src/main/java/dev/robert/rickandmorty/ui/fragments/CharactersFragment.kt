package dev.robert.rickandmorty.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
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
    private val adapter: CharactersPagingAdapter by lazy { CharactersPagingAdapter(
        CharactersPagingAdapter.OnclickListener { results, picture, color ->
            findNavController().navigate(
                CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailsFragment(
                    results, picture, color
                )
            )
        }) }
    private var job: Job? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCharactersListBinding.inflate(inflater, container, false)
        val view = binding.root
        setAdapter()
        setRefresh()
        setUpSearchView()
        filterCharacters()

        return view
    }

    private fun filterCharacters() {
        binding.filterImageView.setOnClickListener {
            findNavController().navigate(R.id.action_charactersFragment_to_filterFragment)
        }
    }


    private fun setRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            getCharacter(null, true)
        }
        binding.inputLayout.apply {
            editText?.text = null
            isFocusable = false
        }
        hideSoftKeyboard()
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
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val scrollPosition = (recyclerView
                        .layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()

                    if (scrollPosition != null) {
                        if (scrollPosition >= 1) {
                            //do something
                        } else {
                            //do something
                        }
                    }
                }
            })
            adapter.addLoadStateListener { loadState ->
                if (loadState.refresh is LoadState.Loading && adapter.snapshot().isEmpty()) {
                    binding.progressCircular.isVisible = true
                    binding.swipeRefreshLayout.isRefreshing = false
                } else {
                    binding.progressCircular.isVisible = false
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                //if there is error a textview will show the error encountered.
                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error

                    else -> null
                }
                if (adapter.snapshot().isEmpty()) {
                    error?.let {
                        binding.errorTextView.text =
                            if (it.error.localizedMessage.toString() == "HTTP 404") ({
                                binding.errorTextView.text = getString(R.string.no_results_found)
                            }).toString()
                            else {
                                it.error.localizedMessage
                            }
                        binding.errorTextView.isVisible = true
                    }
                } else
                    binding.errorTextView.isVisible = false
            }
        }

    }

    private fun performSearch(searchString: String) {
        if (searchString.isNotEmpty()) {
            getCharacter(searchString, true)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpSearchView() {
        binding.inputLayout.editText?.apply {
            setOnTouchListener { _, motionEvent ->
                if (motionEvent.action == 0) {
                    binding.searchView.isFocusable = true
                    binding.searchView.isFocusableInTouchMode = true
                    binding.searchView.requestFocus()
                    return@setOnTouchListener true
                }
                false
            }
            setOnEditorActionListener { _, _, _ ->
                performSearch(binding.inputLayout.editText?.text.toString())
                hideSoftKeyboard()
                true
            }
        }
    }


    private fun hideSoftKeyboard() {
        val view = requireActivity().currentFocus
        view?.let {
            val inputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

    }

    override fun onResume() {
        super.onResume()
        //setting the status bar color back
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.black)
        val navBar = activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navBar.visibility = View.VISIBLE
    }

    override fun onPause() {
        super.onPause()
        binding.searchView.isFocusable = false
    }

}