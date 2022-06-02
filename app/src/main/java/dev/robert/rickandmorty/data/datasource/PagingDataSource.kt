package dev.robert.rickandmorty.data.datasource

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.robert.rickandmorty.api.ApiService
import dev.robert.rickandmorty.model.CharactersResult
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
        val pageNumber = params.key ?: 1
        /*val loadSize =
            if (searchQuery != null) params.loadSize
            else 100*/

        return try {
            val response = apiService.getCharacters(pageNumber, searchQuery)
            var nextPage : Int? = null
            if(response.info.next.isNotEmpty()) {
                val uri = Uri.parse(response.info.next)
                nextPage = uri.getQueryParameter("page")?.toInt()

            }
             LoadResult.Page(
                data = response.charactersResults,
                prevKey = null,
                nextKey = nextPage
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

}