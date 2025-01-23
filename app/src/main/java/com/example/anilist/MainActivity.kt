package com.example.anilist

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.designsystem.theme.AniListTheme
import com.example.designsystem.theme.AppSettingsVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Setting theme to avoid bug with post splashscreen theme
        setTheme(R.style.Theme_AniList)

        enableEdgeToEdge()

        val splashScreen = installSplashScreen()

        splashScreen.setKeepOnScreenCondition { true }
        CoroutineScope(Dispatchers.Main).launch {
            //Delay for longer animation
            delay(700)
            splashScreen.setKeepOnScreenCondition { false }
        }

        setContent {
            val appSettingsVM = hiltViewModel<AppSettingsVM>()
            val theme by appSettingsVM.theme.collectAsState(initial = "default")
            val colorSystem by appSettingsVM.colorSystem.collectAsState(initial = "default")

            AniListTheme(
                theme = theme,
                colorSystem = colorSystem
            ) {
                val prefs = this.getPreferences(Context.MODE_PRIVATE)

                NavGraph(
                    prefs = prefs,
                    appSettingsVM = appSettingsVM
                )
            }
        }
    }
}