package com.example.data.remote.repos

import com.example.data.remote.api_instance.AniListApiInstance
import com.example.data.remote.models.common_models.common_request.CommonRequest
import com.example.data.remote.models.media_details_models.media_details_response.MediaDetailsResponse
import com.example.data.repos.MediaDetailsScreenRepo
import com.google.gson.Gson
import javax.inject.Inject

class MediaDetailsScreenRepoImpl @Inject constructor(
    private val apiInstance: AniListApiInstance
): MediaDetailsScreenRepo {

    override suspend fun getMediaDetailsById(id: Int): MediaDetailsResponse {
        val query = """
            query (${"$"}id: Int) {
              Media(id: ${"$"}id) {
                seasonYear
                type
                format
                episodes
                chapters
                title {
                  english
                  romaji
                  native
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
                popularity

                genres

                description

                characters {
                  nodes {
                    id
                    image {
                      large
                    }
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
                  rank
                }

                recommendations {
                  nodes {
                    mediaRecommendation {
                      type
                      id
                      startDate {
                        year
                        month
                        day
                      }
                      averageScore
                      coverImage {
                        large
                      }
                      title {
                        english
                        romaji
                      }
                      format
                      episodes
                      favourites
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