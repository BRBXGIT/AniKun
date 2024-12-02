package com.example.data.remote.models.media_details_models.user_media_lists_response

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("MediaListCollection")
    val mediaListCollection: MediaListCollection
)