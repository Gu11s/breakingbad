package com.example.breakingbad.datasource.remote

import com.example.breakingbad.model.CharacterResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterAPI {

    @GET("characters")
    fun getCharacters(
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?
    ): Call<List<CharacterResponse>>
}