package com.example.data.remote.repos

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.remote.api_instance.AniListApiInstance
import com.example.data.remote.models.anime_models.user_anime_list_response.Media
import com.example.data.remote.models.common_models.common_request.CommonRequest
import com.example.data.remote.models.profile_models.user_data.AniListUser
import com.example.data.remote.paging.UserAnimeListPS
import com.example.data.repos.ProfileScreenRepo
import kotlinx.coroutines.flow.Flow
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

    override suspend fun getUserAnimeList(
        accessToken: String,
        userName: String,
        status: String
    ): Flow<PagingData<Media>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                UserAnimeListPS(
                    apiInstance = apiInstance,
                    userName = userName,
                    status = status,
                    accessToken = accessToken
                )
            }
        ).flow
    }
}