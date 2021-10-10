package com.example.breakingbad.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.breakingbad.R
import com.example.breakingbad.model.CharacterResponse
import com.example.breakingbad.ui.utils.CharacterListAdapter
import com.example.breakingbad.viewmodel.CharacterViewModel
import kotlinx.android.synthetic.main.fragment_characters.*

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
//            progressBar = view.findViewById(R.id.pb_loadingView)

        characterViewModel = ViewModelProviders.of(this).get(CharacterViewModel::class.java)
        characterViewModel.refresh(100, 0)

        rvCharacterList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = characterListAdapter
        }

        refreshLayout.setOnRefreshListener {
            rvCharacterList.visibility = View.GONE
            tv_listError.visibility = View.GONE
            pb_loadingView.visibility = View.VISIBLE
            characterViewModel.refresh(100, 0)
            refreshLayout.isRefreshing = false
        }
//        getCharacters()

        observeViewModel()

    }

    fun getCharacters(){
        characterViewModel.getCharacter(100, 0){
            if(it != null){
                val characterList: ArrayList<CharacterResponse> = it as ArrayList<CharacterResponse>
                rvCharacterList.adapter = CharacterListAdapter(characterList)
            }
        }
    }


    fun observeViewModel(){
        characterViewModel.characters.observe(viewLifecycleOwner, Observer{characters ->
            characters?.let{
                rvCharacterList.visibility = View.VISIBLE
                characterListAdapter.updateCharacterList(characters)
            }
        })

        characterViewModel.charactersLoadError.observe(viewLifecycleOwner, Observer{isError ->
            isError?.let{
                tv_listError.visibility =
                    if(it){
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
            }
        })

        characterViewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let{
                pb_loadingView.visibility =
                    if(it){
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
            }
        })
    }
}