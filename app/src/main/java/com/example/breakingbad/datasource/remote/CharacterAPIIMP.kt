package com.example.breakingbad.datasource.remote

import android.util.Log
import com.example.breakingbad.model.GetCharactersResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharacterAPIIMP: CharacterAPI {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.breakingbadapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val characterAPI: CharacterAPI = retrofit.create(CharacterAPI::class.java)


    override fun getCharacters(limit: Int?, offset: Int?): Call<List<GetCharactersResponse>> {
        Log.i("Getting character response", "Done")
        return characterAPI.getCharacters(limit, offset)
    }


}