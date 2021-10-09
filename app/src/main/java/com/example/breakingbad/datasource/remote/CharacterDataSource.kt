package com.example.breakingbad.datasource.remote

import com.example.breakingbad.model.CharacterResponse

interface CharacterDataSource {

    fun getCharacters(limit: Int?, offset: Int?, onResult:(List<CharacterResponse>?) -> Unit)
}