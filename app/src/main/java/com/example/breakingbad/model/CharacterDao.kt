package com.example.breakingbad.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

//Data Access Object
@Dao
interface CharacterDao {
    //insert data into the database
    @Insert
    suspend fun insertAll(vararg characters: CharacterResponse): List<Long>

    @Query("SELECT * FROM characterresponse") //'characterresponse' table name
    suspend fun getAllCharacters(): List<CharacterResponse>

    @Query("SELECT * FROM characterresponse WHERE uuid = :characterId")
    suspend fun getCharacter(characterId: Int): CharacterResponse

    @Query("DELETE FROM characterresponse")
    suspend fun deleteAllCharacters()
}