package com.example.data.remote.models.character_info_response

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("Character")
    val character: Character
)