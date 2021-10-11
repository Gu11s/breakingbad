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

    //get data from database in order by favorite
    @Query("SELECT * FROM characterresponse ORDER BY isFavorite desc") //'characterresponse' table name
    suspend fun getAllCharacters(): List<CharacterResponse>

    //get single character data from database to show in detail fragment
    @Query("SELECT * FROM characterresponse WHERE uuid = :characterId")
    suspend fun getCharacter(characterId: Int): CharacterResponse

    //delete all the data in table
    @Query("DELETE FROM characterresponse")
    suspend fun deleteAllCharacters()

    //update single character in table if it is favorite
    @Query("UPDATE characterresponse SET isFavorite = :isFavorite WHERE uuid = :characterId")
    suspend fun setCharacterFavorite(isFavorite: Boolean, characterId: Int): Int
}