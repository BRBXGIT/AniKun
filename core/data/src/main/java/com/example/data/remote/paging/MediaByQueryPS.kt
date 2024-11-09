package com.example.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.remote.api_instance.AniListApiInstance
import com.example.data.remote.models.common_models.common_request.CommonRequest
import com.example.data.remote.models.common_models.media_by_query_response.Media as MediaByQueryMedia
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

class MediaByQueryPS(
    private val apiInstance: AniListApiInstance,
    private val search: String,
    type: String
): PagingSource<Int, MediaByQueryMedia>() {
    override fun getRefreshKey(state: PagingState<Int, MediaByQueryMedia>): Int? {
        return state.anchorPosition
    }

    private val query = """
        query (${"$"}search: String!, ${"$"}page: Int, ${"$"}perPage: Int) {
          Page(page: ${"$"}page, perPage: ${"$"}perPage) {
            pageInfo {
              hasNextPage
            }
            media(search: ${"$"}search, type: $type) {
              id
              title {
                romaji
                english
              }
              averageScore
              genres
            }
          }
        }
    """.trimIndent()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MediaByQueryMedia> {
        val startPage = params.key ?: 1
        val perPage = 20

        val variables = mapOf(
            "search" to search,
            "page" to startPage,
            "perPage" to perPage
        )

        val jsonVariables = Gson().toJson(variables)

        return try {
            val media = apiInstance.getMediaByQuery(
                CommonRequest(
                    query = query,
                    variables = jsonVariables
                )
            )
            val nextPage = if(media.data.page.pageInfo.hasNextPage) (startPage + 1) else null

            LoadResult.Page(
                data = media.data.page.media,
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