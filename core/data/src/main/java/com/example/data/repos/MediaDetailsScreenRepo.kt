package com.example.data.repos

import com.example.data.remote.models.media_details_models.media_details_response.MediaDetailsResponse

interface MediaDetailsScreenRepo {

    suspend fun getMediaDetailsById(id: Int): MediaDetailsResponse
}