package com.example.data.remote.models.media_details_models.media_details_response

data class MediaRecommendation(
    val type: String,
    val id: Int,
    val startDate: StartDate,
    val averageScore: Int,
    val coverImage: CoverImage,
    val title: Title,
    val format: String,
    val episodes: Int,
    val favourites: Int,
)