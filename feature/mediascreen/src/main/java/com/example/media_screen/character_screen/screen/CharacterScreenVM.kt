package com.example.media_screen.character_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.dispatchers.AniKunDispatchers
import com.example.common.dispatchers.Dispatcher
import com.example.data.remote.models.character_info_response.CharacterDetailsResponse
import com.example.data.remote.repos.CharacterScreenRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterScreenVM @Inject constructor(
    private val repository: CharacterScreenRepoImpl,
    @Dispatcher(AniKunDispatchers.IO) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {

    private val _characterDetails = MutableStateFlow(CharacterDetailsResponse())
    val characterDetails = _characterDetails.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        CharacterDetailsResponse()
    )

    fun fetchCharacterDetails(characterId: Int) {
        viewModelScope.launch(dispatcherIo) {
            try {
                _characterDetails.value = repository.getCharacterDetails(characterId)
            } catch(e: Exception) {
                _characterDetails.value = CharacterDetailsResponse(
                    exception = e.message.toString()
                )
            }
        }
    }
}