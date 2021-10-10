package com.example.breakingbad.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.breakingbad.model.CharacterDatabase
import com.example.breakingbad.model.CharacterResponse
import kotlinx.coroutines.launch

//class DetailViewModel: ViewModel() {
class DetailViewModel(application: Application) : BaseViewModel(application) {

    val characterLiveData = MutableLiveData<CharacterResponse>()

    fun fetch(uuid: Int) {

        launch {
            val character = CharacterDatabase(getApplication()).characterDao().getCharacter(uuid)
            characterLiveData.value = character
        }
    }
}