package com.example.data.remote.models.user_by_query_details_response

data class User(
    val avatar: Avatar,
    val favourites: Favourites,
    val id: Int,
    val name: String,
    val statistics: Statistics
)