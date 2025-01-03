package com.example.auth.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.dispatchers.AniKunDispatchers
import com.example.common.dispatchers.Dispatcher
import com.example.data.local.user_db.AniKunUser
import com.example.data.remote.repos.AuthRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthScreenVM @Inject constructor(
    private val repository: AuthRepoImpl,
    @Dispatcher(AniKunDispatchers.IO) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {

    fun upsertUser(accessToken: String) {
        viewModelScope.launch(dispatcherIo) {
            repository.upsertUser(
                AniKunUser(
                    accessToken
                )
            )
        }
    }
}