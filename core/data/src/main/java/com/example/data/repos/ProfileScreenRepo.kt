package com.example.data.repos

import com.example.data.remote.models.add_episode_response.AddAnimeEpisodeResponse
import com.example.data.remote.models.profile_models.change_list_type_response.ChangeMediaListTypeResponse
import com.example.data.remote.models.profile_models.user_anime_list_response.UserAnimeListsResponse
import com.example.data.remote.models.profile_models.user_by_query_response.UserByQueryResponse
import com.example.data.remote.models.profile_models.user_data_response.AniListUser
import com.example.data.remote.models.profile_models.user_manga_list_response.UserMangaListsResponse

interface ProfileScreenRepo {

    suspend fun getAniListUser(accessToken: String): AniListUser

    suspend fun getUserAnimeLists(userName: String): UserAnimeListsResponse

    suspend fun getUserMangaLists(userName: String): UserMangaListsResponse

    suspend fun getUserByQuery(
        userName: String
    ): UserByQueryResponse

    suspend fun changeMediaListType(
        mediaId: Int,
        listType: String,
        accessToken: String
    ): ChangeMediaListTypeResponse

    suspend fun addAnimeEpisode(
        animeId: Int,
        progress: Int,
        accessToken: String
    ): AddAnimeEpisodeResponse
}