package dev.robert.rickandmorty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.robert.rickandmorty.data.repository.MainApiRepository
import dev.robert.rickandmorty.model.location.LocationsResult
import dev.robert.rickandmorty.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class CharacterLocationViewModel @Inject constructor(
    private val repository: MainApiRepository
) : ViewModel() {

    private var currentLocationList: Flow<PagingData<LocationsResult>>? = null

    fun getLocationList(): Flow<PagingData<LocationsResult>> {
        val newLocationList = repository.getLocation().cachedIn(viewModelScope)
        currentLocationList = newLocationList
        return newLocationList
    }

    /*suspend fun getLocationList() = flow {
        emit(repository.getLocation().cachedIn(viewModelScope))
    }*/

}