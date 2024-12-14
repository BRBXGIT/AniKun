package com.example.media_screen.media_screen.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.dispatchers.AniKunDispatchers
import com.example.common.dispatchers.Dispatcher
import com.example.data.remote.models.profile_models.user_favorites_response.Favourites
import com.example.data.remote.repos.CommonRepoImpl
import com.example.data.remote.repos.FavoritesScreenRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaFavoritesScreensSharedVM @Inject constructor(
    private val repository: FavoritesScreenRepoImpl,
    private val commonRepository: CommonRepoImpl,
    @Dispatcher(AniKunDispatchers.IO) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {

    private val _userFavorites = MutableStateFlow(Favourites())
    val userFavorites = _userFavorites.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        Favourites()
    )

    private val _userFavoritesException = MutableStateFlow<String?>("")
    val userFavoritesException = _userFavoritesException.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        null
    )

    fun fetchUserFavorites(userName: String) {
        viewModelScope.launch(dispatcherIo) {
            try {
                val response = repository.getUserFavorites(userName).data

                if(response != null) {
                    _userFavorites.value = response.user.favourites
                    _userFavoritesException.value = null
                }
            } catch(e: Exception) {
                _userFavorites.value = Favourites(
                    exception = e.message.toString()
                )
            }
        }
    }

    fun toggleFavorite(mediaType: String, mediaId: Int) {
        Log.d("CCCC", mediaType)

        viewModelScope.launch(dispatcherIo) {
            try {
                commonRepository.getAniKunUser().collect { aniKunUser ->
                    val response = repository.toggleFavorite(
                        animeId = mediaId,
                        mangaId = mediaId,
                        mediaType = mediaType,
                        accessToken = "Bearer ${aniKunUser[0].accessToken}"
                    ).data

                    if(response != null) {
                        _userFavorites.value = response.toggleFavourite
                        _userFavoritesException.value = null
                    }
                }
            } catch(e: Exception) {
                Log.d("CCCC", e.message.toString())
                _userFavoritesException.value = e.message.toString()
            }
        }
    }

    private val _chosenContentType = MutableStateFlow(false)
    val chosenContentType = _chosenContentType.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        false
    )

    fun setContentType(contentType: Boolean) {
        _chosenContentType.value = contentType
    }
}