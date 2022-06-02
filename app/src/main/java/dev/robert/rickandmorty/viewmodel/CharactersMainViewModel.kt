package dev.robert.rickandmorty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.robert.rickandmorty.data.repository.CharactersRepository
import dev.robert.rickandmorty.model.CharactersResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharactersMainViewModel
@Inject constructor(
    private val repository: CharactersRepository
) : ViewModel() {

    private var currentResults: Flow<PagingData<CharactersResult>>? = null

    fun getCharacters(searchString: String?): Flow<PagingData<CharactersResult>> {
        val newList = repository.getCharacters(searchString).cachedIn(viewModelScope)
        currentResults = newList
        return newList
    }

}