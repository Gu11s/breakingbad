package com.example.breakingbad.ui.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.breakingbad.R
import com.example.breakingbad.databinding.CharacterItemBinding
import com.example.breakingbad.model.CharacterResponse
import com.example.breakingbad.ui.CharactersFragmentDirections
import com.example.breakingbad.viewmodel.CharacterViewModel
import kotlinx.android.synthetic.main.character_item.view.*

class CharacterListAdapter(private val characterList: ArrayList<CharacterResponse>, private var actions: SetCharacterFavoriteActions) :
    RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder>(), CharacterClickListener {

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

        holder.view.character = characterList[position]
        holder.view.listener = this

        if(characterList[position].isFavorite){
            holder.view.ivFavorite.setImageResource(R.drawable.ic_favorite)
        }else{
            holder.view.ivFavorite.setImageResource(R.drawable.ic_outline_favorite)
        }

        holder.view.ivFavorite.setOnClickListener {
            if(!characterList[position].isFavorite){
                holder.view.ivFavorite.setImageResource(R.drawable.ic_favorite)
                characterList[position].isFavorite = true
            }else{
                holder.view.ivFavorite.setImageResource(R.drawable.ic_outline_favorite)
                characterList[position].isFavorite = false
            }

            actions.setFavorite(characterList[position].isFavorite, characterList[position].uuid)

        }

    }

    override fun onCharacterClicked(v: View) {
        val uuid = v.tv_characterId.text.toString().toInt()
        val action = CharactersFragmentDirections.actionCharactersFragmentToDetailFragment()
        action.characterUuid = uuid
        Navigation.findNavController(v).navigate(action)
    }
}