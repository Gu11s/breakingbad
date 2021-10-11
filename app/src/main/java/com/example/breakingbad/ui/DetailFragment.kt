package com.example.breakingbad.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.breakingbad.R
import com.example.breakingbad.databinding.FragmentDetailBinding
import com.example.breakingbad.util.getProgressDrawable
import com.example.breakingbad.util.loadImage
import com.example.breakingbad.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.character_item.*
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private var characterUuid = 0
    private lateinit var dataBinding: FragmentDetailBinding
    private var isFavorite: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            characterUuid = DetailFragmentArgs.fromBundle(it).characterUuid
        }

//        ib_isFavorite.setOnClickListener {
//            isFavorite = if(!isFavorite){
//                ib_isFavorite.setImageResource(R.drawable.ic_favorite)
//                true
//            }else{
//                ib_isFavorite.setImageResource(R.drawable.ic_outline_favorite)
//                false
//            }
//        }

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.fetch(characterUuid)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.characterLiveData.observe(viewLifecycleOwner, Observer { character ->
            character?.let {
//                tv_detailCharacterName.text = character.characterName
//                tv_detailCharacterOccupation.text = character.characterOccupation.toString()
//                tv_detailCharacterStatus.text = character.characterStatus
//                tv_detailCharacterPortrayedBy.text = character.characterPortrayedBy
//                context?.let {
//                    iv_detailCharacterImage.loadImage(
//                        character.characterImage,
//                        getProgressDrawable(it)
//                    )
//                }
                dataBinding.character = character
            }
        })
    }
}