package com.example.media_screen.media_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.dispatchers.AniKunDispatchers
import com.example.common.dispatchers.Dispatcher
import com.example.data.remote.models.profile_models.user_anime_list_response.UserAnimeListsResponse
import com.example.data.remote.models.profile_models.user_by_query_response.UserByQueryResponse
import com.example.data.remote.models.profile_models.user_data_response.AniListUser
import com.example.data.remote.models.profile_models.user_manga_list_response.UserMangaListsResponse
import com.example.data.remote.repos.CommonRepoImpl
import com.example.data.remote.repos.ProfileScreenRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaProfileScreensSharedVM @Inject constructor(
    commonRepository: CommonRepoImpl,
    private val repository: ProfileScreenRepoImpl,
    @Dispatcher(AniKunDispatchers.IO) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {

    private val aniKunUser = commonRepository.getAniKunUser()

    @OptIn(ExperimentalCoroutinesApi::class)
    val aniListUser = aniKunUser.flatMapLatest { aniKunUser ->
        flow {
            try {
                emit(
                    repository.getAniListUser(
                        accessToken = "Bearer ${aniKunUser[0].accessToken}"
                    )
                )
            } catch(e: Exception) {
                emit(
                    AniListUser(
                        exception = e.message.toString()
                    )
                )
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        AniListUser()
    )

    private val _chosenContentType = MutableStateFlow(false)
    val chosenContentType = _chosenContentType.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        false
    )

    fun setContentType(contentType: Boolean) {
        _chosenContentType.value = contentType
    }

    private val query = MutableStateFlow("")

    fun setQuery(searchBarQuery: String) {
        query.value = searchBarQuery
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val userByQuery = query
        .flatMapLatest { query ->
            flow {
                try {
                    emit(repository.getUserByQuery(query))
                } catch(e: Exception) {
                    emit(
                        UserByQueryResponse(
                            exception = e.message.toString()
                        )
                    )
                }
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            UserByQueryResponse()
        )

    //User anime lists
    @OptIn(ExperimentalCoroutinesApi::class)
    val userAnimeLists = aniListUser.flatMapLatest { aniListUser ->
        flow {
            try {
                emit(
                    repository.getUserAnimeLists(userName = aniListUser.data.viewer.name)
                )
            } catch(e: Exception) {
                emit(
                    UserAnimeListsResponse(
                        exception = e.message.toString()
                    )
                )
            }
        }
    }

    //User manga lists
    @OptIn(ExperimentalCoroutinesApi::class)
    val userMangaLists = aniListUser.flatMapLatest { aniListUser ->
        flow {
            try {
                emit(
                    repository.getUserMangaLists(userName = aniListUser.data.viewer.name)
                )
            } catch(e: Exception) {
                emit(
                    UserMangaListsResponse(
                        exception = e.message.toString()
                    )
                )
            }
        }
    }

    fun changeMediaListType(mediaId: Int, listType: String) {
        viewModelScope.launch(dispatcherIo) {
            aniKunUser.collect { aniKunUser ->
                repository.changeMediaListType(
                    mediaId = mediaId,
                    listType = listType,
                    accessToken = "Bearer ${aniKunUser[0].accessToken}"
                )
            }
        }
    }
}