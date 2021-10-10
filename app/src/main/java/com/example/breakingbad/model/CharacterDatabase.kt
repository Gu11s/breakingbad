package com.example.breakingbad.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = arrayOf(CharacterResponse::class), version = 1)
@TypeConverters(Converters::class)
abstract class CharacterDatabase: RoomDatabase() {

    //using singleton to avoid many uses of the database at the same time
    abstract fun characterDao(): CharacterDao

    companion object {
        @Volatile
        private var instance: CharacterDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CharacterDatabase::class.java,
            "characterdatabase"
        ).build()
    }
}