package com.example.data.remote.models.profile_models.user_anime_list_response

data class MediaX(
    val averageScore: Int,
    val coverImage: CoverImage,
    val description: String,
    val episodes: Int,
    val genres: List<String>,
    val id: Int,
    val title: Title
)