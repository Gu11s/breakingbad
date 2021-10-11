package com.example.breakingbad.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.breakingbad.model.CharacterDatabase
import com.example.breakingbad.model.CharacterResponse
import com.example.breakingbad.repository.CharacterRepository
import com.example.breakingbad.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

//class CharacterViewModel: ViewModel() {
class CharacterViewModel(application: Application) : BaseViewModel(application) {

    private val characterRepository = CharacterRepository()

    private var prefHelper = SharedPreferencesHelper(getApplication())
    private var refreshTime = 5 * 60 * 1000 * 1000 * 100L

    private var flagFirstTime = true

    private val disposable = CompositeDisposable() //retrieve or observe the observable
    val characters = MutableLiveData<List<CharacterResponse>>()
    val charactersLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(limit: Int?, offset: Int?) {
        val upDateTime = prefHelper.getUpdateTime()

        //fetch from remote in a 5min time
//        if (upDateTime != null && upDateTime != 0L && System.nanoTime() - upDateTime < refreshTime) {
//            fetchFromDatabase()
//        } else {
//            fetchFromRemote(limit, offset)
//        }

        if (prefHelper.getFlag() == true) {
            fetchFromRemote(limit, offset)
            flagFirstTime = false
            prefHelper.saveFlag(flagFirstTime)
        } else {
            fetchFromDatabase()
        }
    }

    fun refreshBypassCache(limit: Int?, offset: Int?) {
        fetchFromRemote(limit, offset)
    }

    fun fetchFromDatabase() {
        loading.value = true
        launch {
            val characters = CharacterDatabase(getApplication()).characterDao().getAllCharacters()
            charactersRetrieved(characters)
            Toast.makeText(getApplication(), "FROM DATABASE", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchFromRemote(limit: Int?, offset: Int?) {
        loading.value = true
        disposable.add(
            characterRepository.getCharacterObservable(limit, offset)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<CharacterResponse>>() {
                    override fun onSuccess(characterList: List<CharacterResponse>) {
                        Log.e("ROOM DATABASe", characterList[1].toString())
                        storeCharactersLocally(characterList)
                        Toast.makeText(getApplication(), "FROM ENDPOINT", Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        charactersLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun charactersRetrieved(characterList: List<CharacterResponse>) {
        characters.value = characterList
        charactersLoadError.value = false
        loading.value = false
    }

    fun setToFavorite(isFavorite: Boolean, characterUuid: Int) {
        launch {
            CharacterDatabase(getApplication()).characterDao()
                .setCharacterFavorite(isFavorite, characterUuid)
        }
    }

    private fun storeCharactersLocally(list: List<CharacterResponse>) {
        //implementing coroutines
        launch {
            val dao = CharacterDatabase(getApplication()).characterDao()
            dao.deleteAllCharacters()
            val result = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size) {
                list[i].uuid = result[i].toInt()
                ++i
            }
            list[9].isFavorite = true
            charactersRetrieved(list)
        }
        prefHelper.saveUpdateTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}