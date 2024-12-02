package com.example.data.repos

import com.example.data.remote.models.media_details_models.ani_list_user_response.AniListUserResponse
import com.example.data.remote.models.media_details_models.media_details_response.MediaDetailsResponse
import com.example.data.remote.models.media_details_models.user_media_lists_response.UserMediaListsResponse

interface MediaDetailsScreenRepo {

    suspend fun getMediaDetailsById(id: Int): MediaDetailsResponse

    suspend fun getAniListUser(accessToken: String): AniListUserResponse

    suspend fun getUserMediaLists(userName: String, type: String): UserMediaListsResponse
}