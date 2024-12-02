package com.example.data.repos

import androidx.paging.PagingData
import com.example.data.local.user_db.AniKunUser
import com.example.data.remote.models.common_models.media_by_query_response.Media as MediaByQueryMedia
import kotlinx.coroutines.flow.Flow

interface CommonRepo {

    fun getMediaByQuery(
        search: String,
        type: String
    ): Flow<PagingData<MediaByQueryMedia>>

    fun getAniKunUser(): Flow<List<AniKunUser>>
}