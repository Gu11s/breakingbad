package com.example.breakingbad.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.breakingbad.model.CharacterResponse

class DetailViewModel: ViewModel() {

    val characterLiveData = MutableLiveData<CharacterResponse>()

    fun fetch(){
//        val character
//        characterLiveData = character
    }
}