package dev.robert.rickandmorty.data.datasource

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.robert.rickandmorty.api.ApiService
import dev.robert.rickandmorty.model.episodes.EpisodesResult
import retrofit2.HttpException
import java.io.IOException

class EpisodesDataSource(
    private val apiService: ApiService,
    private val name : String
): PagingSource<Int, EpisodesResult>() {
    override fun getRefreshKey(state: PagingState<Int, EpisodesResult>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodesResult> {
        val currentPage = params.key ?: 1
        return try {
            val response = apiService.getEpisodes(currentPage, name)
            val nextPage: Int?

            val uri = Uri.parse(response.info.next)
            nextPage = uri.getQueryParameter("page")?.toInt()

            LoadResult.Page(
                data = response.episodesResults,
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e : IOException){
            LoadResult.Error(e)
        } catch (e : HttpException){
            LoadResult.Error(e)
        }
    }
}