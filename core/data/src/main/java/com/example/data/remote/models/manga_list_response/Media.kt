package com.example.data.remote.models.manga_list_response

data class Media(
    val type: String,
    val averageScore: Int,
    val coverImage: CoverImage,
    val genres: List<String>,
    val id: Int,
    val title: Title
)