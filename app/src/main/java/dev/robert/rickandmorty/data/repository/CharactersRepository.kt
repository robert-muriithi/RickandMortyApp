package dev.robert.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import dev.robert.rickandmorty.api.ApiService
import dev.robert.rickandmorty.data.datasource.PagingDataSource
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val apiService: ApiService
) : BaseRepository(){

     fun getCharacters(searchString: String?) = Pager(
        config = PagingConfig(
            enablePlaceholders = false,
            pageSize = 50),
        pagingSourceFactory = {
            PagingDataSource(
                apiService, searchString
            )
        }
    ).flow

}