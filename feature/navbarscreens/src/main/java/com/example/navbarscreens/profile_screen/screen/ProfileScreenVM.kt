package com.example.navbarscreens.profile_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.data.remote.models.profile_models.user_data.AniListUser
import com.example.data.remote.repos.CommonRepoImpl
import com.example.data.remote.repos.ProfileScreenRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
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

    val userCurrentAnime = aniKunUser.flatMapLatest { aniKunUser ->
        val aniListUser = repository.getAniListUser(
            accessToken = "Bearer ${aniKunUser[0].accessToken}"
        )
        repository.getUserAnimeList(
            accessToken = "Bearer ${aniKunUser[0].accessToken}",
            userName = aniListUser.data.viewer.name,
            status = "CURRENT"
        ).cachedIn(viewModelScope)
    }
}