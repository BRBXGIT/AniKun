package com.example.media_screen.media_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.common.dispatchers.AniKunDispatchers
import com.example.common.dispatchers.Dispatcher
import com.example.data.remote.models.media_details_models.media_details_response.MediaDetailsResponse
import com.example.data.remote.repos.MediaDetailsScreenRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaScreenVM @Inject constructor(
    private val repository: MediaDetailsScreenRepoImpl,
    @Dispatcher(AniKunDispatchers.IO) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {

    private val _mediaDetails = MutableStateFlow(
        MediaDetailsResponse(
            data = null,
            exception = null
        )
    )
    val mediaDetails = _mediaDetails.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        MediaDetailsResponse()
    )

    fun fetchMediaDetailsById(mediaId: Int) {
        viewModelScope.launch(dispatcherIo) {
            try {
                _mediaDetails.value = repository.getMediaDetailsById(mediaId)
            } catch(e: Exception) {
                _mediaDetails.value = MediaDetailsResponse(
                    exception = e.message.toString()
                )
            }
        }
    }

    private val _genre = MutableStateFlow("")
    fun setGenre(userGenre: String) {
        _genre.value = userGenre
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val animeByGenre = _genre
        .flatMapLatest { genre->
            repository.getAnimeByGenre(genre).cachedIn(viewModelScope)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val mangaByGenre = _genre
        .flatMapLatest { genre->
            repository.getMangaByGenre(genre).cachedIn(viewModelScope)
        }
}