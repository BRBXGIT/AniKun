package com.example.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.remote.api_instance.AniListApiInstance
import com.example.data.remote.models.common_models.common_request.CommonRequest
import com.example.data.remote.models.profile_models.user_manga_list_response.Media as UserMangaListMedia
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

class UserMangaListPS(
    private val apiInstance: AniListApiInstance,
    private val userName: String,
    private val status: String,
    private val accessToken: String
): PagingSource<Int, UserMangaListMedia>() {

    private val query = """
        query (${"$"}userName: String, ${"$"}page: Int, ${"$"}perPage: Int, ${"$"}status: MediaListStatus) {
          Page(page: ${"$"}page, perPage: ${"$"}perPage) {
            pageInfo {
              hasNextPage
            }
            mediaList: mediaList(userName: ${"$"}userName, type: MANGA, status: ${"$"}status) {
              media {
                id
                title {
                  romaji
                  english
                }
                coverImage {
                  large
                }
                genres
                averageScore
              }
            }
          }
        }
    """.trimIndent()

    override fun getRefreshKey(state: PagingState<Int, UserMangaListMedia>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserMangaListMedia> {
        val startPage = params.key ?: 1
        val perPage = 20

        val variables = mapOf(
            "userName" to userName,
            "status" to status,
            "page" to startPage,
            "perPage" to perPage
        )
        val jsonVariables = Gson().toJson(variables)

        return try {
            val anime = apiInstance.getUserMangaList(
                body = CommonRequest(
                    query = query,
                    variables = jsonVariables
                ),
                accessToken = accessToken
            )
            val nextPage = if(anime.data.page.pageInfo.hasNextPage) (startPage + 1) else null

            LoadResult.Page(
                data = anime.data.page.mediaList,
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