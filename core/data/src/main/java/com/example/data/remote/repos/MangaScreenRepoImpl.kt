package com.example.data.remote.repos

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.remote.api_instance.AniListApiInstance
import com.example.data.remote.models.manga_list_response.Media as MangaListMedia
import com.example.data.remote.paging.MangaListsPS
import com.example.data.repos.MangaScreenRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MangaScreenRepoImpl @Inject constructor(
    private val apiInstance: AniListApiInstance
): MangaScreenRepo {

    override fun getMangaList(
        sort: String,
        countryOfOrigin: String
    ): Flow<PagingData<MangaListMedia>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                MangaListsPS(
                    apiInstance = apiInstance,
                    sort = sort,
                    countryOfOrigin = countryOfOrigin
                )
            }
        ).flow
    }
}