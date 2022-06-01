package dev.robert.rickandmorty.api

import dev.robert.rickandmorty.model.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("character")
    suspend fun getCharacters(
        @Query("offset") offset : Int,
        @Query("limit") limit : Int
    ) : CharactersResponse

}