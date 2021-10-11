package com.example.breakingbad.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.breakingbad.R
import com.example.breakingbad.databinding.FragmentDetailBinding
import com.example.breakingbad.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private var characterUuid = 0
    private lateinit var dataBinding: FragmentDetailBinding

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

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.fetch(characterUuid)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.characterLiveData.observe(viewLifecycleOwner, Observer { character ->
            character?.let {
                dataBinding.character = character

                if (character.isFavorite) {
                    ib_isFavorite.setImageResource(R.drawable.ic_favorite)
                } else {
                    ib_isFavorite.setImageResource(R.drawable.ic_outline_favorite)
                }
            }
        })
    }
}