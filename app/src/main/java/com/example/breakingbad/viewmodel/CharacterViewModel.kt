package com.example.breakingbad.viewmodel

import com.example.breakingbad.model.GetCharactersResponse
import com.example.breakingbad.repository.CharacterRepository

class CharacterViewModel {

    private val characterRepository = CharacterRepository()

    fun getCharacter(limit: Int?, offset: Int?, onResult:(List<GetCharactersResponse>?) -> Unit) =
        characterRepository.getCharacters(limit, offset, onResult)
}