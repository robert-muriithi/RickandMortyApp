package dev.robert.rickandmorty.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.robert.rickandmorty.data.repository.MainApiRepository
import dev.robert.rickandmorty.utils.Resource
import dev.robert.rickandmorty.utils.extractId
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val repository: MainApiRepository
) : ViewModel() {
    suspend fun getSingleCharacter(url : String) = flow{
        val id = url.extractId()
        emit(Resource.Loading())
        emit(repository.getSingleCharacter(id))
    }
}