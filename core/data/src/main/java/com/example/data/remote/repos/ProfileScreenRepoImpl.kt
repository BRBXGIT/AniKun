package com.example.data.remote.repos

import com.example.data.remote.api_instance.AniListApiInstance
import com.example.data.remote.models.add_episode_response.AddAnimeEpisodeResponse
import com.example.data.remote.models.common_models.common_request.CommonRequest
import com.example.data.remote.models.profile_models.change_list_type_response.ChangeMediaListTypeResponse
import com.example.data.remote.models.profile_models.user_anime_list_response.UserAnimeListsResponse
import com.example.data.remote.models.profile_models.user_by_query_response.UserByQueryResponse
import com.example.data.remote.models.profile_models.user_data_response.AniListUser
import com.example.data.remote.models.profile_models.user_manga_list_response.UserMangaListsResponse
import com.example.data.repos.ProfileScreenRepo
import com.google.gson.Gson
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
                    progress
                    media {
                      type
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
                      type
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

    override suspend fun changeMediaListType(
        mediaId: Int,
        listType: String,
        accessToken: String
    ): ChangeMediaListTypeResponse {
        val query = """
            mutation (${"$"}mediaId: Int, ${"$"}status: MediaListStatus) {
              SaveMediaListEntry(mediaId: ${"$"}mediaId, status: ${"$"}status) {
                id
              }
            }
        """.trimIndent()

        val variables = mapOf(
            "mediaId" to mediaId,
            "status" to listType
        )
        val jsonVariables = Gson().toJson(variables)

        return apiInstance.changeMediaListType(
            body = CommonRequest(
                query = query,
                variables = jsonVariables
            ),
            accessToken = accessToken
        )
    }

    override suspend fun addAnimeEpisode(
        userName: String,
        animeId: Int,
        progress: Int,
        accessToken: String
    ): AddAnimeEpisodeResponse {
        val query = """
            mutation(${"$"}mediaId: Int, ${"$"}progress: Int) {
              SaveMediaListEntry(mediaId: ${"$"}mediaId, progress: ${"$"}progress) {
                id
              }
            }
        """.trimIndent()

        val variables = mapOf(
            "mediaId" to animeId,
            "progress" to progress
        )
        val jsonVariables = Gson().toJson(variables)

        return apiInstance.addEpisodeToAnime(
            body = CommonRequest(
                query = query,
                variables = jsonVariables
            ),
            accessToken = accessToken
        )
    }
}