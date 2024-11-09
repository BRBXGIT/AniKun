package com.example.data.remote.repos

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.remote.api_instance.AniListApiInstance
import com.example.data.remote.models.common_models.media_by_query_response.Media as MediaByQueryMedia
import com.example.data.remote.paging.MediaByQueryPS
import com.example.data.repos.CommonRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CommonRepoImpl @Inject constructor(
    private val apiInstance: AniListApiInstance
): CommonRepo {

    override fun getMediaByQuery(search: String, type: String): Flow<PagingData<MediaByQueryMedia>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { MediaByQueryPS(apiInstance, search, type) }
        ).flow
    }
}