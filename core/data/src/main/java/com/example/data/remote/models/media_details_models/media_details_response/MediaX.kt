package com.example.data.remote.models.media_details_models.media_details_response

data class MediaX(
    val coverImage: CoverImage,
    val episodes: Int,
    val format: String,
    val seasonYear: Int,
    val title: Title
)