package com.example.breakingbad.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.breakingbad.R
import com.example.breakingbad.model.CharacterResponse
import com.example.breakingbad.ui.utils.CharacterListAdapter
import com.example.breakingbad.viewmodel.CharacterViewModel

class CharactersFragment : Fragment() {

    private lateinit var characterViewModel: CharacterViewModel
    private val characterListAdapter = CharacterListAdapter(arrayListOf())

    lateinit var rvCharacterList : RecyclerView
    lateinit var progressBar : ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_characters, container, false)
        characterViewModel = CharacterViewModel()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvCharacterList = view.findViewById(R.id.rv_characterList)
        progressBar = view.findViewById(R.id.pb_loadingView)

        rvCharacterList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = characterListAdapter
        }

        getCharacters()

    }

    fun getCharacters(){
        characterViewModel.getCharacter(100, 0){
            if(it != null){
                val characterList: ArrayList<CharacterResponse> = it as ArrayList<CharacterResponse>
                rvCharacterList.adapter = CharacterListAdapter(characterList)
            }
        }
    }
}