package com.example.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.remote.api_instance.AniListApiInstance
import com.example.data.remote.models.anime_models.trending_now.request.TrendingNowAnimeRequest
import com.example.data.remote.models.anime_models.trending_now.response.Media
import retrofit2.HttpException
import java.io.IOException

class TrendingAnimePS(
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
                  title {
                    english
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

        return try {
            val anime = apiInstance.getAnime(
                TrendingNowAnimeRequest(
                    query = query,
                    variables = mapOf("page" to startPage, "perPage" to perPage)
                )
            )
            val nextPage = if(!anime.data.page.pageInfo.hasNextPage) null else startPage + 1

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