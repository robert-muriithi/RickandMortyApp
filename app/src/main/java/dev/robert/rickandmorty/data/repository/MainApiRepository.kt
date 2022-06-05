package dev.robert.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import dev.robert.rickandmorty.api.ApiService
import dev.robert.rickandmorty.data.datasource.CharactersDataSource
import dev.robert.rickandmorty.data.datasource.LocationDataSource
import javax.inject.Inject

class MainApiRepository @Inject constructor(
    private val apiService: ApiService
) : BaseRepository() {

    fun getCharacters(searchString: String?) = Pager(
        config = PagingConfig(
            enablePlaceholders = false,
            pageSize = 50
        ),
        pagingSourceFactory = {
            CharactersDataSource(
                apiService, searchString
            )
        }
    ).flow

    suspend fun getSingleCharacter(id: Int) = safeApiCall {
        apiService.getCharacterDetails(id)
    }


    fun getLocation() = Pager(
        config = PagingConfig(
            enablePlaceholders = false,
            pageSize = 50
        ),
        pagingSourceFactory = {
            LocationDataSource(
                apiService
            )
        }
    ).flow

    suspend fun getSingleLocation(id: Int) = safeApiCall {
        apiService.getSingleLocation(id)
    }

}