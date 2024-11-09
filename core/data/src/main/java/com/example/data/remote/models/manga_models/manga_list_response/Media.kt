package com.example.data.remote.models.manga_models.manga_list_response

data class Media(
    val averageScore: Int,
    val genres: List<String>,
    val id: Int,
    val title: Title
)