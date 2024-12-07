package com.example.data.remote.models.profile_models.user_favorites_response

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("User")
    val user: User
)