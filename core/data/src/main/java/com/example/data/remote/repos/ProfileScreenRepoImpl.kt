package com.example.data.remote.repos

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.remote.api_instance.AniListApiInstance
import com.example.data.remote.models.profile_models.user_anime_list_response.Media as UserAnimeListMedia
import com.example.data.remote.models.common_models.common_request.CommonRequest
import com.example.data.remote.models.profile_models.user_by_query_response.UserByQueryResponse
import com.example.data.remote.models.profile_models.user_data_response.AniListUser
import com.example.data.remote.models.profile_models.user_manga_list_response.Media as UserMangaListMedia
import com.example.data.remote.paging.UserAnimeListPS
import com.example.data.remote.paging.UserMangaListPS
import com.example.data.repos.ProfileScreenRepo
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileScreenRepoImpl @Inject constructor(
    private val apiInstance: AniListApiInstance
): ProfileScreenRepo {

    override suspend fun getAniListUser(accessToken: String): AniListUser {
        val userDataQuery = """
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

        val userDataVariables = ""

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
    ): Flow<PagingData<UserAnimeListMedia>> {
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

    override suspend fun getUserMangaList(
        accessToken: String,
        userName: String,
        status: String
    ): Flow<PagingData<UserMangaListMedia>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                UserMangaListPS(
                    apiInstance = apiInstance,
                    userName = userName,
                    status = status,
                    accessToken = accessToken
                )
            }
        ).flow
    }

    override suspend fun getUserByQuery(userName: String): UserByQueryResponse {
        val userByQueryQuery = """
           query (${"$"}userName: String) {
             User(name: ${"$"}userName) {
               id
               name
               avatar {
                 large
               }
             }
           }
        """.trimIndent()

        val variables = mapOf(
            "userName" to userName,
        )
        val jsonVariables = Gson().toJson(variables)

        return apiInstance.getUserByQuery(
            CommonRequest(
                query = userByQueryQuery,
                variables = jsonVariables
            )
        )
    }
}