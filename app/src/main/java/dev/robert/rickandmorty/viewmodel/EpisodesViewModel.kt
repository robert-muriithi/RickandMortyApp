package dev.robert.rickandmorty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.robert.rickandmorty.data.repository.MainApiRepository
import dev.robert.rickandmorty.model.episodes.EpisodesResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val repository: MainApiRepository
) : ViewModel() {

    private var currentList : Flow<PagingData<EpisodesResult>>? = null

    fun getEpisodes( name : String) : Flow<PagingData<EpisodesResult>> {
        if (currentList == null) {
            currentList = repository.getEpisodes(name).cachedIn(viewModelScope)
        }
        return currentList!!
    }
}