package com.example.data.repos

import com.example.data.remote.models.media_details_models.media_details_response.MediaDetailsResponse
import retrofit2.Response

interface MediaDetailsScreenRepo {

    suspend fun getMediaDetailsById(id: Int): Response<MediaDetailsResponse>
}