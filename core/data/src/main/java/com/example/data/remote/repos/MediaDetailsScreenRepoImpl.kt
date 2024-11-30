package com.example.data.remote.repos

import com.example.data.remote.api_instance.AniListApiInstance
import com.example.data.remote.models.common_models.common_request.CommonRequest
import com.example.data.remote.models.media_details_response.MediaDetailsResponse
import com.example.data.repos.MediaDetailsScreenRepo
import com.google.gson.Gson
import javax.inject.Inject

class MediaDetailsScreenRepoImpl @Inject constructor(
    private val apiInstance: AniListApiInstance
): MediaDetailsScreenRepo {

    override suspend fun getMediaDetailsById(id: Int): MediaDetailsResponse {
        val query = """
            query (${"$"}id: Int) {
              Media(id: ${"$"}id, type: ANIME) {
                seasonYear
                format
                episodes
                title {
                  english
                  romaji
                }
                coverImage {
                  large
                }
                bannerImage
                nextAiringEpisode {
                  airingAt
                  episode
                }

                averageScore
                favourites

                genres

                description

                characters {
                  nodes {
                    name {
                      full
                    }
                  }
                }

                duration
                source
                status
                startDate {
                  day
                  month
                  year
                }
                endDate {
                  day
                  month
                  year
                }

                season

                studios {
                  nodes {
                    id
                    name
                  }
                }

                tags {
                  name
                }

                recommendations {
                  nodes {
                    media {
                      coverImage {
                        large
                      }
                      format
                      title {
                        english
                        romaji
                      }
                      episodes
                      seasonYear
                    }
                  }
                }

                externalLinks {
                  site
                  color
                  icon
                  url
                }
              }
            }
        """.trimIndent()

        val variables = mapOf(
            "id" to id
        )
        val jsonVariables = Gson().toJson(variables)

        return apiInstance.getMediaDetailsById(
            body = CommonRequest(
                query = query,
                variables = jsonVariables
            )
        )
    }
}