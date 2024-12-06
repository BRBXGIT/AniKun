package com.example.data.repos

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.example.data.remote.models.manga_list_response.Media as MangaListMedia

interface MangaScreenRepo {

    fun getMangaList(): Flow<PagingData<MangaListMedia>>
}