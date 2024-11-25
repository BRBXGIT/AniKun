package com.example.data.remote.models.profile_models.user_data_response

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("Viewer")
    val viewer: Viewer = Viewer()
)