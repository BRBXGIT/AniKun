package com.example.data.remote.models.anime_models.user_anime_list_response

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("Page")
    val page: Page
)