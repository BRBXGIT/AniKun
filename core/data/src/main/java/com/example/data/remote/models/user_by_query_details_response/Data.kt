package com.example.data.remote.models.user_by_query_details_response

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("User")
    val user: User
)