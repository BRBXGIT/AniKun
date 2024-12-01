package com.example.data.remote.models.media_details_response

data class MediaRecommendation(
    val averageScore: Int,
    val coverImage: CoverImage,
    val title: Title,
    val seasonYear: Int,
    val format: String,
    val episodes: Int,
    val favourites: Int
)