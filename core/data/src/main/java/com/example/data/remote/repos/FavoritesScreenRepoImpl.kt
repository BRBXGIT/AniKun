package com.example.data.remote.repos

import com.example.data.remote.api_instance.AniListApiInstance
import com.example.data.remote.models.common_models.common_request.CommonRequest
import com.example.data.remote.models.profile_models.user_favorites_response.UserFavoritesResponse
import com.example.data.repos.FavoritesScreenRepo
import com.google.gson.Gson
import javax.inject.Inject

class FavoritesScreenRepoImpl @Inject constructor(
    private val apiInstance: AniListApiInstance
): FavoritesScreenRepo {

    override suspend fun getUserFavorites(userName: String): UserFavoritesResponse {
        val query = """
            query (${"$"}userName: String) {
              User(name: ${"$"}userName) {
                favourites {
                  anime {
                    nodes {
                      id
                      averageScore
                      title {
                        romaji
                        english
                      }
                      coverImage {
                        large
                      }
                      description
                      genres
                      episodes
                    }
                  }
                  manga {
                    nodes {
                      id
                      averageScore
                      title {
                        romaji
                        english
                      }
                      coverImage {
                        large
                      }
                      genres
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

        return apiInstance.getUserFavorites(
            body = CommonRequest(
                query = query,
                variables = jsonVariables
            )
        )
    }
}