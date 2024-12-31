package com.example.data.repos

import androidx.paging.PagingData
import com.example.data.remote.models.media_details_models.media_details_response.MediaDetailsResponse
import kotlinx.coroutines.flow.Flow
import com.example.data.remote.models.anime_list_response.Media as AnimeListMedia
import com.example.data.remote.models.manga_list_response.Media as MangaListMedia

interface MediaDetailsScreenRepo {

    suspend fun getMediaDetailsById(id: Int): MediaDetailsResponse

    suspend fun getAnimeByGenre(genre: String): Flow<PagingData<AnimeListMedia>>

    suspend fun getMangaByGenre(genre: String): Flow<PagingData<MangaListMedia>>
}