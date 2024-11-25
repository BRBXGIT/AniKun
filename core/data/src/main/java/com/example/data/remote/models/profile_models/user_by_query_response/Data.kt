package com.example.data.remote.models.profile_models.user_by_query_response

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("User")
    val user: User = User()
)