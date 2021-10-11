package com.example.breakingbad.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromList(list:List<String?>):String{
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromString(value: String): List<String?>{
        val listType = object : TypeToken<List<String?>>() {}.type
        return Gson().fromJson(value,listType)
    }

//    @TypeConverter
//    fun fromListToInt(list: List<Int?>):Int{
//        val gson = Gson()
//        return gson.toJson(list).toString().toInt()
//    }
//
//    @TypeConverter
//    fun fromInt(value: Int):List<Int?>{
//        val listType = object : TypeToken<List<Int?>>() {}.type
//        return Gson().fromJson(value, listType)
//    }

}