package com.example.userscreen.user_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.dispatchers.AniKunDispatchers
import com.example.common.dispatchers.Dispatcher
import com.example.data.remote.models.user_by_query_details_response.UserByQueryDetailsResponse
import com.example.data.remote.repos.UserScreenRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserScreenVM @Inject constructor(
    private val repository: UserScreenRepoImpl,
    @Dispatcher(AniKunDispatchers.IO) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {

    private val _userDetails = MutableStateFlow(UserByQueryDetailsResponse())
    val userDetails = _userDetails.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        UserByQueryDetailsResponse()
    )

    fun fetchUserDetails(userName: String) {
        viewModelScope.launch(dispatcherIo) {
            try {
                _userDetails.value = repository.getUserByQueryDetails(userName)
            } catch(e: Exception) {
                _userDetails.value = UserByQueryDetailsResponse(
                    exception = e.message.toString()
                )
            }
        }
    }
}