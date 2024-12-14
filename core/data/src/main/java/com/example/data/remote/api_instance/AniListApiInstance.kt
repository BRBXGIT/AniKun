package com.example.data.remote.api_instance

import com.example.data.remote.models.anime_list_response.AnimeListResponse
import com.example.data.remote.models.common_models.common_request.CommonRequest
import com.example.data.remote.models.common_models.media_by_query_response.MediaByQueryResponse
import com.example.data.remote.models.manga_list_response.MangaListsResponse
import com.example.data.remote.models.media_details_models.media_details_response.MediaDetailsResponse
import com.example.data.remote.models.profile_models.change_list_type_response.ChangeMediaListTypeResponse
import com.example.data.remote.models.profile_models.toggle_favorite_response.ToggleFavoriteResponse
import com.example.data.remote.models.profile_models.user_anime_list_response.UserAnimeListsResponse
import com.example.data.remote.models.profile_models.user_by_query_response.UserByQueryResponse
import com.example.data.remote.models.profile_models.user_data_response.AniListUser
import com.example.data.remote.models.profile_models.user_favorites_response.UserFavoritesResponse
import com.example.data.remote.models.profile_models.user_manga_list_response.UserMangaListsResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AniListApiInstance {

    @POST(".")
    suspend fun getTrendingAnimeList(
        @Body body: CommonRequest
    ): AnimeListResponse

    @POST(".")
    suspend fun getTrendingMangaList(
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
    suspend fun getUserAnimeLists(
        @Body body: CommonRequest
    ): UserAnimeListsResponse

    @POST(".")
    suspend fun getUserMangaLists(
        @Body body: CommonRequest
    ): UserMangaListsResponse

    @POST(".")
    suspend fun getUserByQuery(
        @Body body: CommonRequest
    ): UserByQueryResponse

    @POST(".")
    suspend fun getMediaDetailsById(
        @Body body: CommonRequest
    ): MediaDetailsResponse

    @POST(".")
    suspend fun getUserFavorites(
        @Body body: CommonRequest
    ): UserFavoritesResponse

    @POST(".")
    suspend fun toggleFavorite(
        @Body body: CommonRequest,
        @Header("Authorization") accessToken: String
    ): ToggleFavoriteResponse

    @POST(".")
    suspend fun changeMediaListType(
        @Body body: CommonRequest,
        @Header("Authorization") accessToken: String
    ): ChangeMediaListTypeResponse
}