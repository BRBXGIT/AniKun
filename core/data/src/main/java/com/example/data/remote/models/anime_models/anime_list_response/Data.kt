package com.example.data.remote.models.anime_models.anime_list_response

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("Page")
    val page: Page
)