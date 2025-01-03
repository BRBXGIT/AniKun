package com.example.data.remote.models.user_by_query_details_response

data class Node(
    val averageScore: Int,
    val coverImage: CoverImage,
    val description: String,
    val episodes: Int,
    val genres: List<String>,
    val id: Int,
    val title: Title,
    val type: String
)