package com.example.breakingbad.ui.utils

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.breakingbad.R
import com.example.breakingbad.databinding.CharacterItemBinding
import com.example.breakingbad.model.CharacterResponse
import com.example.breakingbad.ui.CharactersFragment
import com.example.breakingbad.ui.CharactersFragmentDirections
import com.example.breakingbad.util.getProgressDrawable
import com.example.breakingbad.util.loadImage
import kotlinx.android.synthetic.main.character_item.view.*

class CharacterListAdapter(private val characterList: ArrayList<CharacterResponse>) :
    RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder>() {

    //    class CharacterViewHolder(var view: View): RecyclerView.ViewHolder(view)
    class CharacterViewHolder(var view: CharacterItemBinding) : RecyclerView.ViewHolder(view.root)


    fun updateCharacterList(newCharacterList: List<CharacterResponse>) {
        characterList.clear()
        characterList.addAll(newCharacterList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
//        val view = inflater.inflate(R.layout.character_item, parent, false)
        val view = DataBindingUtil.inflate<CharacterItemBinding>(
            inflater,
            R.layout.character_item,
            parent,
            false
        )

        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.setIsRecyclable(false)

//        holder.view.tv_characterName.text = characterList[position].characterName
//        holder.view.tv_characterNickName.text = characterList[position].characterNickname
//        holder.view.iv_characterImage.loadImage(
//            characterList[position].characterImage,
//            getProgressDrawable(holder.view.iv_characterImage.context)
//        )
//        holder.view.setOnClickListener {
//            Navigation.findNavController(it)
//                .navigate(CharactersFragmentDirections.actionCharactersFragmentToDetailFragment())
//        }

        holder.view.character = characterList[position]

    }
}