package com.example.data.repos

import com.example.data.remote.models.common_models.common_request.CommonRequest
import com.example.data.remote.models.profile_models.user_data.UserData

interface ProfileScreenRepo {

    suspend fun getUser(
        commonRequest: CommonRequest,
        accessToken: String
    ): UserData
}