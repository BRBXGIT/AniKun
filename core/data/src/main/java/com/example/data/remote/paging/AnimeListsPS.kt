package com.example.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.remote.api_instance.AniListApiInstance
import com.example.data.remote.models.anime_models.anime_list_response.Media as AnimeListMedia
import com.example.data.remote.models.common_models.common_request.CommonRequest
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

class AnimeListsPS(
    private val apiInstance: AniListApiInstance,
    private val season: String?,
    private val seasonYear: Int?,
    sort: String,
): PagingSource<Int, AnimeListMedia>() {

    private val query = """
        query (${"$"}page: Int, ${"$"}perPage: Int) {
              Page(page: ${"$"}page, perPage: ${"$"}perPage) {
                pageInfo {
                  hasNextPage
                }
                media(sort: $sort, type: ANIME) {
                  id
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
                  averageScore
                }
              }
            }
    """.trimIndent()

    private val seasonQuery = """
        query (${"$"}page: Int, ${"$"}perPage: Int, ${"$"}season: MediaSeason, ${"$"}seasonYear: Int) {
              Page(page: ${"$"}page, perPage: ${"$"}perPage) {
                pageInfo {
                  hasNextPage
                }
                media(sort: $sort, season: ${"$"}season, seasonYear: ${"$"}seasonYear, type: ANIME) {
                  id
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
                  averageScore
                }
              }
            }
    """.trimIndent()

    override fun getRefreshKey(state: PagingState<Int, AnimeListMedia>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeListMedia> {
        val startPage = params.key ?: 1
        val perPage = 20

        val variables = mapOf(
            "page" to startPage,
            "perPage" to perPage,
            "season" to season,
            "seasonYear" to seasonYear
        )

        val jsonVariables = Gson().toJson(variables)

        return try {
            val anime = apiInstance.getAnimeList(
                CommonRequest(
                    query = if(season != null) seasonQuery else query,
                    variables = jsonVariables
                )
            )
            val nextPage = if(anime.data.page.pageInfo.hasNextPage) (startPage + 1) else null

            LoadResult.Page(
                data = anime.data.page.media,
                prevKey = if(startPage == 1) null else startPage - 1,
                nextKey = nextPage
            )

        } catch(e: IOException) {
            LoadResult.Error(e)
        } catch(e: HttpException) {
            LoadResult.Error(e)
        }
    }
}