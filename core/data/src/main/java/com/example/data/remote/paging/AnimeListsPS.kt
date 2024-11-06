package com.example.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.remote.api_instance.AniListApiInstance
import com.example.data.remote.models.anime_models.request.TrendingNowAnimeRequest
import com.example.data.remote.models.anime_models.response.Media
import retrofit2.HttpException
import java.io.IOException

class AnimeListsPS(
    private val apiInstance: AniListApiInstance,
    sort: String
): PagingSource<Int, Media>() {

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

    override fun getRefreshKey(state: PagingState<Int, Media>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Media> {
        val startPage = params.key ?: 1
        val perPage = params.loadSize

        val variables = """
            {
              "page": $startPage,
              "perPage": $perPage
            }
        """.trimIndent()

        return try {
            val anime = apiInstance.getAnimeList(
                TrendingNowAnimeRequest(
                    query = query,
                    variables = variables
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