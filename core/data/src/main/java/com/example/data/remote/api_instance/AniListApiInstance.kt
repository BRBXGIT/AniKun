package com.example.data.remote.api_instance

import com.example.data.remote.models.common_models.common_request.CommonRequest
import com.example.data.remote.models.anime_list_response.AnimeListResponse
import com.example.data.remote.models.profile_models.user_anime_list_response.UserAnimeListResponse
import com.example.data.remote.models.common_models.media_by_query_response.MediaByQueryResponse
import com.example.data.remote.models.manga_list_response.MangaListsResponse
import com.example.data.remote.models.media_details_models.ani_list_user_response.AniListUserResponse
import com.example.data.remote.models.media_details_models.media_details_response.MediaDetailsResponse
import com.example.data.remote.models.media_details_models.user_media_lists_response.UserMediaListsResponse
import com.example.data.remote.models.profile_models.user_by_query_response.UserByQueryResponse
import com.example.data.remote.models.profile_models.user_data_response.AniListUser
import com.example.data.remote.models.profile_models.user_manga_list_response.UserMangaListResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AniListApiInstance {

    @POST(".")
    suspend fun getAnimeList(
        @Body body: CommonRequest
    ): AnimeListResponse

    @POST(".")
    suspend fun getMangaList(
        @Body body: CommonRequest
    ): MangaListsResponse

    @POST(".")
    suspend fun getMediaByQuery(
        @Body body: CommonRequest
    ): MediaByQueryResponse

    @POST(".")
    suspend fun getAniListUser(
        @Body body: CommonRequest,
        @Header("Authorization") accessToken: String
    ): AniListUser

    @POST(".")
    suspend fun getUserAnimeList(
        @Body body: CommonRequest,
        @Header("Authorization") accessToken: String
    ): UserAnimeListResponse

    @POST(".")
    suspend fun getUserMangaList(
        @Body body: CommonRequest,
        @Header("Authorization") accessToken: String
    ): UserMangaListResponse

    @POST(".")
    suspend fun getUserByQuery(
        @Body body: CommonRequest
    ): UserByQueryResponse

    @POST(".")
    suspend fun getMediaDetailsById(
        @Body body: CommonRequest
    ): MediaDetailsResponse

    @POST(".")
    suspend fun getUserMediaLists(
        @Body body: CommonRequest
    ): UserMediaListsResponse

    @POST(".")
    suspend fun getAniListUserId(
        @Body body: CommonRequest,
        @Header("Authorization") accessToken: String
    ): AniListUserResponse
}