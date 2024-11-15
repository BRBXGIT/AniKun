package com.example.data.remote.repos

import com.example.data.remote.api_instance.AniListApiInstance
import com.example.data.remote.models.common_models.common_request.CommonRequest
import com.example.data.remote.models.profile_models.user_data.UserData
import com.example.data.repos.ProfileScreenRepo
import javax.inject.Inject

class ProfileScreenRepoImpl @Inject constructor(
    private val apiInstance: AniListApiInstance
): ProfileScreenRepo {

    override suspend fun getUser(
        commonRequest: CommonRequest,
        accessToken: String
    ): UserData {
        return apiInstance.getUser(commonRequest, accessToken)
    }
}