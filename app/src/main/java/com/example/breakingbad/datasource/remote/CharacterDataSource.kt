package com.example.breakingbad.datasource.remote

import com.example.breakingbad.model.GetCharactersResponse

interface CharacterDataSource {

    fun getCharacters(limit: Int?, offset: Int?, onResult:(List<GetCharactersResponse>?) -> Unit)
}