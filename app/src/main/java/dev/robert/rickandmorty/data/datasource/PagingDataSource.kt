package dev.robert.rickandmorty.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.robert.rickandmorty.api.ApiService
import dev.robert.rickandmorty.model.CharactersResult
import dev.robert.rickandmorty.utils.SEARCH_LOAD_SIZE
import dev.robert.rickandmorty.utils.STARTING_OFFSET_INDEX
import retrofit2.HttpException
import java.io.IOException

class PagingDataSource(
    private val apiService: ApiService,
    private val searchQuery: String?
) : PagingSource<Int, CharactersResult>(){
    override fun getRefreshKey(state: PagingState<Int, CharactersResult>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharactersResult> {
        val offset = params.key ?: 0
        val loadSize = if (searchQuery != null) params.loadSize
        else 100

        return try {
            val response = apiService.getCharacters(loadSize, offset)

             LoadResult.Page(
                data = response.charactersResults,
                prevKey = if (offset == 1) null else offset - loadSize,
                nextKey = offset + loadSize
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

}