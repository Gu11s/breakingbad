package com.example.breakingbad.datasource.remote

import android.util.Log
import com.example.breakingbad.model.CharacterResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

class CharacterAPIIMP: CharacterAPI {

    private val BASE_URL = "https://www.breakingbadapi.com/api/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    private val characterAPI: CharacterAPI = retrofit.create(CharacterAPI::class.java)

    override fun getCharacters(limit: Int?, offset: Int?): Call<List<CharacterResponse>> {
        Log.i("Get character response", "Done")
        return characterAPI.getCharacters(limit, offset)
    }

    override fun getCharactersObservable(limit: Int?, offset: Int?): Single<List<CharacterResponse>> {
        Log.i("Get character response", "Done")
        return characterAPI.getCharactersObservable(limit, offset)
    }


}