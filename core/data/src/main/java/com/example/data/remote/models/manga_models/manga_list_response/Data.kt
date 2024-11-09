package com.example.data.remote.models.manga_models.manga_list_response

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("Page")
    val page: Page
)