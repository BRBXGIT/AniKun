package com.example.data.remote.repos

import com.example.data.remote.api_instance.AniListApiInstance
import com.example.data.remote.models.common_models.common_request.CommonRequest
import com.example.data.remote.models.user_by_query_details_response.UserByQueryDetailsResponse
import com.example.data.repos.UserScreenRepo
import com.google.gson.Gson
import javax.inject.Inject

class UserScreenRepoImpl @Inject constructor(
    private val apiInstance: AniListApiInstance
): UserScreenRepo {

    override suspend fun getUserByQueryDetails(userName: String): UserByQueryDetailsResponse {
        val query = """
            query(${"$"}userName: String) {
              User(name: ${"$"}userName) {
                id
                name
                avatar {
                  large
                }
                statistics {
                  anime {
                    count
                    minutesWatched
                    episodesWatched
                  }
                  manga {
                    count
                    chaptersRead
                    volumesRead
                  }
                }
                favourites {
                  anime {
                    nodes {
                      type
                      id
                      averageScore
                      episodes
                      title {
                        english
                        romaji
                      }
                      coverImage {
                        large
                      }
                      description
                      genres
                    }
                  }
                  manga {
                    nodes {
                      type
                      id
                      averageScore
                      coverImage {
                        large
                      }
                      genres
                      title {
                        english
                        romaji
                      }
                    }
                  }
                  characters {
                    nodes {
                      name {
                        full
                        native
                      }
                      image {
                        large
                      }
                      id
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

        return apiInstance.getUserByQueryDetails(
            body = CommonRequest(
                query = query,
                variables = jsonVariables
            )
        )
    }
}