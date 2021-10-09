package com.example.breakingbad.viewmodel

import com.example.breakingbad.model.CharacterResponse
import com.example.breakingbad.repository.CharacterRepository

class CharacterViewModel {

    private val characterRepository = CharacterRepository()

    fun getCharacter(limit: Int?, offset: Int?, onResult:(List<CharacterResponse>?) -> Unit) =
        characterRepository.getCharacters(limit, offset, onResult)
}