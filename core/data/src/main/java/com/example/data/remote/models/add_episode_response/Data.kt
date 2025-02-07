package com.example.data.remote.models.add_episode_response

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("SaveMediaListEntry")
    val saveMediaListEntry: SaveMediaListEntry
)