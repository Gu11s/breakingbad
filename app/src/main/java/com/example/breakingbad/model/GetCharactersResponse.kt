package com.example.breakingbad.model

import com.google.gson.annotations.SerializedName

data class GetCharactersResponse(

    @SerializedName("char_id")
    val characterId: Int?,

    @SerializedName("name")
    val characterName: String?,

    @SerializedName("birthday")
    val characterBirthday: String?,

    @SerializedName("occupation")
    val characterOccupation: List<String?>,

    @SerializedName("img")
    val characterImage: String?,

    @SerializedName("status")
    val characterStatus: String?,

    @SerializedName("nickname")
    val characterNickname: String?,

    @SerializedName("appearance")
    val characterAppearance: List<Int?>,

    @SerializedName("portrayed")
    val characterPortrayedBy: String?,

    @SerializedName("category")
    val characterCategory: String?,

    @SerializedName("better_call_saul_appearance")
    val characterBetterCallSaulAppearance: List<Int?>
)