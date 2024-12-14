package com.example.data.remote.models.profile_models.user_favorites_response

data class Favourites(
    val anime: Anime = Anime(),
    val manga: Manga = Manga(),
    val exception: String? = null
)