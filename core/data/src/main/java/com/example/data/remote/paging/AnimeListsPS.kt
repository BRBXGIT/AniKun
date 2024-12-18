package com.example.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.remote.api_instance.AniListApiInstance
import com.example.data.remote.models.common_models.common_request.CommonRequest
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException
import com.example.data.remote.models.anime_list_response.Media as AnimeListMedia

class AnimeListsPS(
    private val apiInstance: AniListApiInstance
): PagingSource<Int, AnimeListMedia>() {

    private val query = """
        query (${"$"}page: Int, ${"$"}perPage: Int) {
              Page(page: ${"$"}page, perPage: ${"$"}perPage) {
                pageInfo {
                  hasNextPage
                }
                media(sort: TRENDING_DESC, type: ANIME) {
                  type
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
            "perPage" to perPage
        )
        val jsonVariables = Gson().toJson(variables)

        return try {
            val anime = apiInstance.getTrendingAnimeList(
                CommonRequest(
                    query = query,
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