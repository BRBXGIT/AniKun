package com.example.anilist

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.designsystem.theme.AniListTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_AniList)

        enableEdgeToEdge()

        val splashScreen = installSplashScreen()

        splashScreen.setKeepOnScreenCondition { true }
        CoroutineScope(Dispatchers.Main).launch {
            delay(700)
            splashScreen.setKeepOnScreenCondition { false }
        }

        setContent {
            AniListTheme {
                val prefs = this.getPreferences(Context.MODE_PRIVATE)

                NavGraph(prefs)
            }
        }
    }
}