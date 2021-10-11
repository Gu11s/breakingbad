package com.example.breakingbad.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

//Data Access Object
@Dao
interface CharacterDao {
    //insert data into the database
    @Insert
    suspend fun insertAll(vararg characters: CharacterResponse): List<Long>

    @Query("SELECT * FROM characterresponse ORDER BY isFavorite desc") //'characterresponse' table name
    suspend fun getAllCharacters(): List<CharacterResponse>

    @Query("SELECT * FROM characterresponse WHERE uuid = :characterId")
    suspend fun getCharacter(characterId: Int): CharacterResponse

    @Query("DELETE FROM characterresponse")
    suspend fun deleteAllCharacters()

    @Query("UPDATE characterresponse SET isFavorite = :isFavorite WHERE uuid = :characterId")
    suspend fun setCharacterFavorite(isFavorite: Boolean, characterId: Int): Int

//    @Update
//    suspend fun updateCharacter(characterResponse: CharacterResponse)

//    @Query("SELECT * FROM characterresponse")
//    suspend fun saveFavoriteCharacter()
}