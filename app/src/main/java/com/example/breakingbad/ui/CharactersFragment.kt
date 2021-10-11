package com.example.breakingbad.ui

import android.app.Application
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
import com.example.breakingbad.ui.utils.SetCharacterFavoriteActions
import com.example.breakingbad.viewmodel.CharacterViewModel
import kotlinx.android.synthetic.main.fragment_characters.*

class CharactersFragment : Fragment(), SetCharacterFavoriteActions {

    private lateinit var characterViewModel: CharacterViewModel
    private val characterListAdapter = CharacterListAdapter(arrayListOf(), this)

    lateinit var rvCharacterList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_characters, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvCharacterList = view.findViewById(R.id.rv_characterList)

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
            characterViewModel.fetchFromDatabase()
            refreshLayout.isRefreshing = false
        }
        observeViewModel()

    }

    fun observeViewModel() {
        characterViewModel.characters.observe(viewLifecycleOwner, Observer { characters ->
            characters?.let {
                rvCharacterList.visibility = View.VISIBLE
                characterListAdapter.updateCharacterList(characters)
            }
        })

        characterViewModel.charactersLoadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                tv_listError.visibility =
                    if (it) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
            }
        })

        characterViewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                pb_loadingView.visibility =
                    if (it) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
            }
        })
    }

    override fun setFavorite(isFavorite: Boolean, characterUuid: Int) {
        characterViewModel.setToFavorite(isFavorite, characterUuid)
    }
}