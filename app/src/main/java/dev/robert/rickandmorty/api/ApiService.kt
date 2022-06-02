package dev.robert.rickandmorty.api

import dev.robert.rickandmorty.model.CharactersResponse
import dev.robert.rickandmorty.model.CharactersResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("name") name: String? = null
    ): CharactersResponse

    @GET("character/{character_id}")
    suspend fun getCharacterDetails(
        @Path("character_id") characterId: Int
    ): CharactersResult

}