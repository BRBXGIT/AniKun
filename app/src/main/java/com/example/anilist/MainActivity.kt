package com.example.anilist

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.designsystem.theme.AniListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_AniList)

        enableEdgeToEdge()
        installSplashScreen()

        setContent {
            AniListTheme {
                val prefs = this.getPreferences(Context.MODE_PRIVATE)

                NavGraph(prefs)
            }
        }
    }
}