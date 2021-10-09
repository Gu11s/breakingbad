package com.example.breakingbad.repository

import android.util.Log
import com.example.breakingbad.datasource.remote.CharacterAPIIMP
import com.example.breakingbad.datasource.remote.CharacterDataSource
import com.example.breakingbad.model.GetCharactersResponse
import retrofit2.*

class CharacterRepository : CharacterDataSource {

    val api = CharacterAPIIMP()

    override fun getCharacters(
        limit: Int?,
        offset: Int?,
        onResult: (List<GetCharactersResponse>?) -> Unit
    ) {
        api.getCharacters(limit, offset).enqueue(object : Callback<List<GetCharactersResponse>> {
            override fun onResponse(
                call: Call<List<GetCharactersResponse>>,
                response: Response<List<GetCharactersResponse>>
            ) {
                if (response.code() == 200) {
                    Log.i("Character response success", response.body().toString())
                    val characterList: List<GetCharactersResponse> = response.body()!!
                    onResult(characterList)
                } else {
                    Log.e("Character response", response.code().toString())
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<List<GetCharactersResponse>>, t: Throwable) {
                Log.e("Character response fail", t.message.toString())
                onResult(null)
            }

        })
    }
}