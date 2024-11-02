package com.example.data.remote.models.anime_models.trending_now.response

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("Page")
    val page: Page
)