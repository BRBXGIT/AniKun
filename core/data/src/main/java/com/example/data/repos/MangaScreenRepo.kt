package com.example.data.repos

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.example.data.remote.models.manga_models.manga_list_response.Media as MangaListMedia

interface MangaScreenRepo {

    fun getMangaList(
        sort: String,
        season: String?,
        seasonYear: Int?
    ): Flow<PagingData<MangaListMedia>>
}