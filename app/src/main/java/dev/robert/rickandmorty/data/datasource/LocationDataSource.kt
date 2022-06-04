package dev.robert.rickandmorty.data.datasource

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.robert.rickandmorty.api.ApiService
import dev.robert.rickandmorty.model.location.LocationsResult

class LocationDataSource(
    private val apiService: ApiService
) : PagingSource<Int, LocationsResult>() {
    override fun getRefreshKey(state: PagingState<Int, LocationsResult>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationsResult> {
        val currentPage = params.key ?: 1

        return try {
            val response = apiService.getLocation(currentPage)
            val nextPage : Int?

            val uri = Uri.parse(response.info.next)
            nextPage = uri.getQueryParameter("page")?.toInt()
            LoadResult.Page(
                data = response.locationsResults,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (e : Exception){
            LoadResult.Error(e)
        }
    }
}