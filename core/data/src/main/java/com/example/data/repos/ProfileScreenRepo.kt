package com.example.data.repos

import com.example.data.remote.models.profile_models.user_data.AniListUser

interface ProfileScreenRepo {

    suspend fun getAniListUser(accessToken: String): AniListUser
}