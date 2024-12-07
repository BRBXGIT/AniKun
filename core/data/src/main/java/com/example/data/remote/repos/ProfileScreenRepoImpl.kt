package com.example.data.remote.repos

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.remote.api_instance.AniListApiInstance
import com.example.data.remote.models.common_models.common_request.CommonRequest
import com.example.data.remote.models.profile_models.user_anime_list_response.UserAnimeListsResponse
import com.example.data.remote.models.profile_models.user_by_query_response.UserByQueryResponse
import com.example.data.remote.models.profile_models.user_data_response.AniListUser
import com.example.data.remote.models.profile_models.user_manga_list_response.UserMangaListsResponse
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

    override suspend fun getUserAnimeLists(userName: String): UserAnimeListsResponse {
        val query = """
            query (${"$"}userName: String) {
              MediaListCollection(userName: ${"$"}userName, type: ANIME) {
                lists {
                  name
                  entries {
                    media {
                      averageScore
                      coverImage {
                        large
                      }
                      description
                      genres
                      id
                      title {
                        english
                        romaji
                      }
                      episodes
                    }
                  }
                }
              }
            }
        """.trimIndent()

        val variables = mapOf(
            "userName" to userName,
        )
        val jsonVariables = Gson().toJson(variables)

        return apiInstance.getUserAnimeLists(
            CommonRequest(
                query = query,
                variables = jsonVariables
            )
        )
    }

    override suspend fun getUserMangaLists(userName: String): UserMangaListsResponse {
        val query = """
            query (${"$"}userName: String) {
              MediaListCollection(userName: ${"$"}userName, type: MANGA) {
                lists {
                  name
                  entries {
                    media {
                      averageScore
                      coverImage {
                        large
                      }
                      genres
                      id
                      title {
                        english
                        romaji
                      }
                    }
                  }
                }
              }
            }
        """.trimIndent()

        val variables = mapOf(
            "userName" to userName,
        )
        val jsonVariables = Gson().toJson(variables)

        return apiInstance.getUserMangaLists(
            CommonRequest(
                query = query,
                variables = jsonVariables
            )
        )
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