package com.example.data.remote.models.anime_models.response

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("Page")
    val page: Page
)