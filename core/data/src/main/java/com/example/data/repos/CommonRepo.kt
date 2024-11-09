package com.example.data.repos

import androidx.paging.PagingData
import com.example.data.remote.models.common_models.media_by_query_response.Media as MediaByQueryMedia
import kotlinx.coroutines.flow.Flow

interface CommonRepo {

    fun getMediaByQuery(
        search: String,
        type: String
    ): Flow<PagingData<MediaByQueryMedia>>
}