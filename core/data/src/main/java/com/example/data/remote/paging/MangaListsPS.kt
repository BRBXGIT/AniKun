package com.example.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.remote.api_instance.AniListApiInstance
import com.example.data.remote.models.common_models.common_request.CommonRequest
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException
import com.example.data.remote.models.manga_list_response.Media as MangaListMedia

class MangaListsPS(
    private val apiInstance: AniListApiInstance,
    private val genre: String? = null
): PagingSource<Int, MangaListMedia>() {

    private val query = """
        query (${"$"}page: Int, ${"$"}perPage: Int) {
              Page(page: ${"$"}page, perPage: ${"$"}perPage) {
                pageInfo {
                  hasNextPage
                }
                media(sort: TRENDING_DESC, type: MANGA) {
                  type
                  id
                  title {
                    english
                    romaji
                  }
                  coverImage {
                    large
                  }
                  genres
                  averageScore
                }
              }
            }
    """.trimIndent()

    private val queryWithGenre = """
        query (${"$"}page: Int, ${"$"}perPage: Int, ${"$"}genre: String) {
              Page(page: ${"$"}page, perPage: ${"$"}perPage) {
                pageInfo {
                  hasNextPage
                }
                media(genre: ${"$"}genre, type: MANGA) {
                  type
                  id
                  title {
                    english
                    romaji
                  }
                  coverImage {
                    large
                  }
                  genres
                  averageScore
                }
              }
            }
    """.trimIndent()

    override fun getRefreshKey(state: PagingState<Int, MangaListMedia>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MangaListMedia> {
        val startPage = params.key ?: 1
        val perPage = 20

        val variables = mapOf(
            "page" to startPage,
            "perPage" to perPage,
        )
        val genreVariables = mapOf(
            "page" to startPage,
            "perPage" to perPage,
            "genre" to genre
        )

        val jsonVariables = if(genre != null) {
            Gson().toJson(genreVariables)
        } else {
            Gson().toJson(variables)
        }

        return try {
            val manga = apiInstance.getMangaList(
                CommonRequest(
                    query = if(genre != null) queryWithGenre else query,
                    variables = jsonVariables
                )
            )
            val nextPage = if(manga.data.page.pageInfo.hasNextPage) (startPage + 1) else null

            LoadResult.Page(
                data = manga.data.page.media,
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