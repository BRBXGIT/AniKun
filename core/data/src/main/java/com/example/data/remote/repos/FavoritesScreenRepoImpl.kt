package com.example.data.remote.repos

import com.example.data.remote.api_instance.AniListApiInstance
import com.example.data.remote.models.common_models.common_request.CommonRequest
import com.example.data.remote.models.profile_models.toggle_favorite_response.ToggleFavoriteResponse
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
                      type
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
                      type
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

        return apiInstance.getUserFavorites(
            body = CommonRequest(
                query = query,
                variables = jsonVariables
            )
        )
    }

    override suspend fun toggleFavorite(
        animeId: Int,
        mangaId: Int,
        characterId: Int,
        mediaType: String,
        accessToken: String
    ): ToggleFavoriteResponse {
        val query = """
            mutation (${"$"}animeId: Int, ${"$"}mangaId: Int, ${"$"}toggleFavouriteCharacterId2: Int) {
              ToggleFavourite(animeId: ${"$"}animeId, mangaId: ${"$"}mangaId, characterId: ${"$"}toggleFavouriteCharacterId2) {
                anime {
                  nodes {
                    id
                    type
                    averageScore
                    coverImage {
                      large
                    }
                    description
                    genres
                    title {
                      romaji
                      english
                    }
                    episodes
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
                      romaji
                      english
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
        """.trimIndent()

        val variables = when(mediaType) {
            "MANGA" -> mapOf("mangaId" to mangaId)
            "ANIME" -> mapOf("animeId" to animeId)
            else -> mapOf("toggleFavouriteCharacterId2" to characterId)
        }
        val jsonVariables = Gson().toJson(variables)

        return apiInstance.toggleFavorite(
            body = CommonRequest(
                query = query,
                variables = jsonVariables
            ),
            accessToken = accessToken
        )
    }
}