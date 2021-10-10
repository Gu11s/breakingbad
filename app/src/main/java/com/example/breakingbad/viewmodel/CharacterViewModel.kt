package com.example.breakingbad.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.breakingbad.model.CharacterDatabase
import com.example.breakingbad.model.CharacterResponse
import com.example.breakingbad.repository.CharacterRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

//class CharacterViewModel: ViewModel() {
class CharacterViewModel(application: Application): BaseViewModel(application) {

    private val characterRepository = CharacterRepository()

    fun getCharacter(limit: Int?, offset: Int?, onResult:(List<CharacterResponse>?) -> Unit) =
        characterRepository.getCharacters(limit, offset, onResult)

    private val disposable = CompositeDisposable() //retrieve or observe the observable
    val characters = MutableLiveData<List<CharacterResponse>>()
    val charactersLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(limit: Int?, offset: Int?){
        fetchFromRemote(limit, offset)
    }

    private fun fetchFromRemote(limit: Int?, offset: Int?){
        loading.value = true
        disposable.add(
            characterRepository.getCharacterObservable(limit, offset)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<CharacterResponse>>(){
                    override fun onSuccess(characterList: List<CharacterResponse>) {
                        storeCharactersLocally(characterList)
                    }

                    override fun onError(e: Throwable) {
                        charactersLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun charactersRetrieved(characterList: List<CharacterResponse>){
        characters.value = characterList
        charactersLoadError.value = false
        loading.value = false
    }

    private fun storeCharactersLocally(list: List<CharacterResponse>){
        //implementing coroutines
        launch {
            val dao = CharacterDatabase(getApplication()).characterDao()
            dao.deleteAllCharacters()
            val result = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size){
                list[i].uuid = result[i].toInt()
                ++i
            }
            charactersRetrieved(list)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}