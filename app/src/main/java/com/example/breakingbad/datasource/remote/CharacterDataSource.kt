package com.example.breakingbad.datasource.remote

import com.example.breakingbad.model.CharacterResponse
import io.reactivex.Single

interface CharacterDataSource {

    fun getCharacters(limit: Int?, offset: Int?, onResult:(List<CharacterResponse>?) -> Unit)

    fun getCharacterObservable(limit: Int?, offset: Int?): Single<List<CharacterResponse>>
}