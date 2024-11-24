package com.example.navbarscreens.profile_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.data.remote.models.profile_models.user_data.AniListUser
import com.example.data.remote.repos.CommonRepoImpl
import com.example.data.remote.repos.ProfileScreenRepoImpl
import com.example.navbarscreens.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProfileScreenVM @Inject constructor(
    commonRepository: CommonRepoImpl,
    private val repository: ProfileScreenRepoImpl
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


    //User anime lists
    @OptIn(ExperimentalCoroutinesApi::class)
    val userWatchingAnime = combine(aniKunUser, aniListUser) { aniKunUser, aniListUser ->
        repository.getUserAnimeList(
            accessToken = "Bearer ${aniKunUser[0].accessToken}",
            userName = aniListUser.data.viewer.name,
            status = Utils.USER_WATCHING_MEDIA_TYPE
        ).cachedIn(viewModelScope)
    }.flatMapLatest { it.cachedIn(viewModelScope) }

    @OptIn(ExperimentalCoroutinesApi::class)
    val userReWatchingAnime = combine(aniKunUser, aniListUser) { aniKunUser, aniListUser ->
        repository.getUserAnimeList(
            accessToken = "Bearer ${aniKunUser[0].accessToken}",
            userName = aniListUser.data.viewer.name,
            status = Utils.USER_RE_WATCHING_MEDIA_TYPE
        ).cachedIn(viewModelScope)
    }.flatMapLatest { it.cachedIn(viewModelScope) }

    @OptIn(ExperimentalCoroutinesApi::class)
    val userCompletedAnime = combine(aniKunUser, aniListUser) { aniKunUser, aniListUser ->
        repository.getUserAnimeList(
            accessToken = "Bearer ${aniKunUser[0].accessToken}",
            userName = aniListUser.data.viewer.name,
            status = Utils.USER_COMPLETED_MEDIA_TYPE
        ).cachedIn(viewModelScope)
    }.flatMapLatest { it.cachedIn(viewModelScope) }

    @OptIn(ExperimentalCoroutinesApi::class)
    val userPausedAnime = combine(aniKunUser, aniListUser) { aniKunUser, aniListUser ->
        repository.getUserAnimeList(
            accessToken = "Bearer ${aniKunUser[0].accessToken}",
            userName = aniListUser.data.viewer.name,
            status = Utils.USER_PAUSED_MEDIA_TYPE
        ).cachedIn(viewModelScope)
    }.flatMapLatest { it.cachedIn(viewModelScope) }

    @OptIn(ExperimentalCoroutinesApi::class)
    val userDroppedAnime = combine(aniKunUser, aniListUser) { aniKunUser, aniListUser ->
        repository.getUserAnimeList(
            accessToken = "Bearer ${aniKunUser[0].accessToken}",
            userName = aniListUser.data.viewer.name,
            status = Utils.USER_DROPPED_MEDIA_TYPE
        ).cachedIn(viewModelScope)
    }.flatMapLatest { it.cachedIn(viewModelScope) }

    @OptIn(ExperimentalCoroutinesApi::class)
    val userPlanningAnime = combine(aniKunUser, aniListUser) { aniKunUser, aniListUser ->
        repository.getUserAnimeList(
            accessToken = "Bearer ${aniKunUser[0].accessToken}",
            userName = aniListUser.data.viewer.name,
            status = Utils.USER_PLANNING_MEDIA_TYPE
        ).cachedIn(viewModelScope)
    }.flatMapLatest { it.cachedIn(viewModelScope) }


    //User manga lists
    @OptIn(ExperimentalCoroutinesApi::class)
    val userReadingManga = combine(aniKunUser, aniListUser) { aniKunUser, aniListUser ->
        repository.getUserMangaList(
            accessToken = "Bearer ${aniKunUser[0].accessToken}",
            userName = aniListUser.data.viewer.name,
            status = Utils.USER_WATCHING_MEDIA_TYPE
        ).cachedIn(viewModelScope)
    }.flatMapLatest { it.cachedIn(viewModelScope) }

    @OptIn(ExperimentalCoroutinesApi::class)
    val userReReadingManga = combine(aniKunUser, aniListUser) { aniKunUser, aniListUser ->
        repository.getUserMangaList(
            accessToken = "Bearer ${aniKunUser[0].accessToken}",
            userName = aniListUser.data.viewer.name,
            status = Utils.USER_RE_WATCHING_MEDIA_TYPE
        ).cachedIn(viewModelScope)
    }.flatMapLatest { it.cachedIn(viewModelScope) }

    @OptIn(ExperimentalCoroutinesApi::class)
    val userCompletedManga = combine(aniKunUser, aniListUser) { aniKunUser, aniListUser ->
        repository.getUserMangaList(
            accessToken = "Bearer ${aniKunUser[0].accessToken}",
            userName = aniListUser.data.viewer.name,
            status = Utils.USER_COMPLETED_MEDIA_TYPE
        ).cachedIn(viewModelScope)
    }.flatMapLatest { it.cachedIn(viewModelScope) }

    @OptIn(ExperimentalCoroutinesApi::class)
    val userPausedManga = combine(aniKunUser, aniListUser) { aniKunUser, aniListUser ->
        repository.getUserMangaList(
            accessToken = "Bearer ${aniKunUser[0].accessToken}",
            userName = aniListUser.data.viewer.name,
            status = Utils.USER_PAUSED_MEDIA_TYPE
        ).cachedIn(viewModelScope)
    }.flatMapLatest { it.cachedIn(viewModelScope) }

    @OptIn(ExperimentalCoroutinesApi::class)
    val userDroppedManga = combine(aniKunUser, aniListUser) { aniKunUser, aniListUser ->
        repository.getUserMangaList(
            accessToken = "Bearer ${aniKunUser[0].accessToken}",
            userName = aniListUser.data.viewer.name,
            status = Utils.USER_DROPPED_MEDIA_TYPE
        ).cachedIn(viewModelScope)
    }.flatMapLatest { it.cachedIn(viewModelScope) }

    @OptIn(ExperimentalCoroutinesApi::class)
    val userPlanningManga = combine(aniKunUser, aniListUser) { aniKunUser, aniListUser ->
        repository.getUserMangaList(
            accessToken = "Bearer ${aniKunUser[0].accessToken}",
            userName = aniListUser.data.viewer.name,
            status = Utils.USER_PLANNING_MEDIA_TYPE
        ).cachedIn(viewModelScope)
    }.flatMapLatest { it.cachedIn(viewModelScope) }
}