package com.example.breakingbad.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//@Entity(tableName = "characters")
@Entity
data class CharacterResponse(

    @ColumnInfo(name = "characterId")
    @SerializedName("char_id")
    val characterId: Int?,

    @ColumnInfo(name = "characterName")
    @SerializedName("name")
    val characterName: String?,

    @ColumnInfo(name = "characterBirthday")
    @SerializedName("birthday")
    val characterBirthday: String?,

    @ColumnInfo(name = "characterOccupation")
    @SerializedName("occupation")
    val characterOccupation: List<String?>,

    @ColumnInfo(name = "characterImage")
    @SerializedName("img")
    val characterImage: String?,

    @ColumnInfo(name = "characterStatus")
    @SerializedName("status")
    val characterStatus: String?,

    @ColumnInfo(name = "characterNickname")
    @SerializedName("nickname")
    val characterNickname: String?,

//    @ColumnInfo(name = "characterAppearance")
//    @SerializedName("appearance")
//    val characterAppearance: List<Int?>,

    @ColumnInfo(name = "characterPortrayedBy")
    @SerializedName("portrayed")
    val characterPortrayedBy: String?,

    @ColumnInfo(name = "characterCategory")
    @SerializedName("category")
    val characterCategory: String?,

//    @ColumnInfo(name = "characterBetterCallSaulAppearance")
//    @SerializedName("better_call_saul_appearance")
//    val characterBetterCallSaulAppearance: List<Int?>
) {
    //generating primary key
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}