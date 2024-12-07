package com.example.navbarscreens.favorites_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.dispatchers.AniKunDispatchers
import com.example.common.dispatchers.Dispatcher
import com.example.data.remote.models.profile_models.user_data_response.AniListUser
import com.example.data.remote.models.profile_models.user_favorites_response.UserFavoritesResponse
import com.example.data.remote.repos.FavoritesScreenRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesScreenVM @Inject constructor(
    private val repository: FavoritesScreenRepoImpl,
    @Dispatcher(AniKunDispatchers.IO) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {

    private val _userFavorites = MutableStateFlow(UserFavoritesResponse())
    val userFavorites = _userFavorites.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        UserFavoritesResponse()
    )

    fun fetchUserFavorites(userName: String) {
        viewModelScope.launch(dispatcherIo) {
            try {
                _userFavorites.value = repository.getUserFavorites(userName)
            } catch(e: Exception) {
                _userFavorites.value = UserFavoritesResponse(exception = e.message.toString())
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