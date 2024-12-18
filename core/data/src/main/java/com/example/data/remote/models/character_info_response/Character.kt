package com.example.data.remote.models.character_info_response

data class Character(
    val description: String,
    val favourites: Int,
    val image: Image,
    val media: Media,
    val name: Name
)