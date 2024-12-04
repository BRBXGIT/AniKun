package com.example.designsystem.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.dispatchers.AniKunDispatchers
import com.example.common.dispatchers.Dispatcher
import com.example.data.local.data_store.AppDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppSettingsVM @Inject constructor(
    private val dataStore: AppDataStore,
    @Dispatcher(AniKunDispatchers.IO) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {

    val theme = dataStore.themeFlow
    val colorSystem = dataStore.colorSystemFlow

    fun changeTheme(theme: String) {
        viewModelScope.launch(dispatcherIo) {
            dataStore.saveTheme(theme)
        }
    }

    fun changeColorSystem(colorSystem: String) {
        viewModelScope.launch(dispatcherIo) {
            dataStore.saveColorSystem(colorSystem)
        }
    }
}