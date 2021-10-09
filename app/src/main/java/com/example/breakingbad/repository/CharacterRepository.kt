package com.example.breakingbad.repository

import android.util.Log
import com.example.breakingbad.datasource.remote.CharacterAPIIMP
import com.example.breakingbad.datasource.remote.CharacterDataSource
import com.example.breakingbad.model.CharacterResponse
import retrofit2.*

class CharacterRepository : CharacterDataSource {

    val api = CharacterAPIIMP()

    override fun getCharacters(
        limit: Int?,
        offset: Int?,
        onResult: (List<CharacterResponse>?) -> Unit
    ) {
        api.getCharacters(limit, offset).enqueue(object : Callback<List<CharacterResponse>> {
            override fun onResponse(
                call: Call<List<CharacterResponse>>,
                response: Response<List<CharacterResponse>>
            ) {
                if (response.code() == 200) {
                    Log.e("Character response success", response.body().toString())
                    val characterList: List<CharacterResponse> = response.body()!!
                    onResult(characterList)
                } else {
                    Log.e("Character response", response.code().toString())
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<List<CharacterResponse>>, t: Throwable) {
                Log.e("Character response fail", t.message.toString())
                onResult(null)
            }

        })
    }
}