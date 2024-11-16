package com.example.data.remote.repos

import com.example.data.remote.api_instance.AniListApiInstance
import com.example.data.remote.models.common_models.common_request.CommonRequest
import com.example.data.remote.models.profile_models.user_data.AniListUser
import com.example.data.repos.ProfileScreenRepo
import javax.inject.Inject

class ProfileScreenRepoImpl @Inject constructor(
    private val apiInstance: AniListApiInstance
): ProfileScreenRepo {

    private val userDataQuery = """
        query {
          Viewer {
            id
            name
            avatar {
              large
            }
          }
        }
    """.trimIndent()

    private val userDataVariables = ""

    override suspend fun getAniListUser(accessToken: String): AniListUser {
        return apiInstance.getAniListUser(
            body = CommonRequest(
                query = userDataQuery,
                variables = userDataVariables
            ),
            accessToken = accessToken
        )
    }
}