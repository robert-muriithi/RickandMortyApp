package dev.robert.rickandmorty.api

import dev.robert.rickandmorty.model.CharactersResponse
import dev.robert.rickandmorty.model.CharactersResult
import dev.robert.rickandmorty.model.episodes.EpisodeResponse
import dev.robert.rickandmorty.model.episodes.EpisodesResult
import dev.robert.rickandmorty.model.location.LocationResponse
import dev.robert.rickandmorty.model.location.LocationsResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("name") name: String? = null
    ): CharactersResponse

    @GET("location")
    suspend fun getLocation(
        @Query("page") page: Int
    ): LocationResponse

    @GET("episode")
    suspend fun getEpisodes(
        @Query("page") page: Int,
        @Query("name") name: String? = null
    ): EpisodeResponse

    @GET("episode/{episode_id}")
    suspend fun getSingleEpisode(
        @Path("episode_id") episodeId: Int
    ): EpisodesResult

    @GET("location/{location_id}")
    suspend fun getSingleLocation(
        @Path("location_id") locationId: Int
    ): LocationsResult

    @GET("character/{character_id}")
    suspend fun getCharacterDetails(
        @Path("character_id") characterId: Int
    ): CharactersResult

}