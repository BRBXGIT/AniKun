package com.example.auth.screen

import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.auth.R
import com.example.auth.sections.AniListAuthPageWebView
import com.example.auth.sections.WebViewTopBar
import com.example.designsystem.icons.AniKunIcons
import com.example.designsystem.theme.mColors
import com.example.designsystem.theme.mShapes
import com.example.designsystem.theme.mTypography
import com.example.navbarscreens.common.navigation.NavBarScreensRoute
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    accessToken: String?,
    navController: NavController,
    viewModel: AuthScreenVM,
    prefs: SharedPreferences,
) {
    var parsedAccessToken by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(accessToken) {
        if(accessToken != null) {
            parsedAccessToken = accessToken.split("=")[1].split("&")[0]
        }
    }

    //Simple fake loading to deceive user
    var isLoading by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(parsedAccessToken) {
        if(parsedAccessToken != "") {
            isLoading = true

            prefs.edit().apply {
                putBoolean("loggedIn", true)
                apply()
            }
            viewModel.upsertUser(parsedAccessToken)

            delay(500)
            isLoading = false

            navController.navigate(NavBarScreensRoute)
        }
    }

    var webViewEnabled by rememberSaveable { mutableStateOf(false) }
    var pageProgress by rememberSaveable { mutableIntStateOf(0) }
    Scaffold(
        topBar = {
            if(webViewEnabled) {
                WebViewTopBar(
                    onCloseClick = { webViewEnabled = false },
                    progress = pageProgress
                )
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.background)
    ) { innerPadding ->
        if(!webViewEnabled) {
            PullToRefreshBox(
                isRefreshing = isLoading,
                onRefresh = {  }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .paint(
                            painterResource(id = R.drawable.auth_screen_bg),
                            contentScale = ContentScale.Crop
                        )
                        .padding(
                            top = innerPadding.calculateTopPadding(),
                            bottom = innerPadding.calculateBottomPadding(),
                            start = 16.dp,
                            end = 16.dp
                        )
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text(
                            text = "AniKun",
                            style = mTypography.titleMedium.copy(
                                color = Color(0xFFA3C9FE),
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Text(
                            text = "Powered by AniList",
                            style = mTypography.bodyMedium.copy(
                                color = Color(0xffffffff)
                            )
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Hello in AniKun",
                                style = mTypography.titleMedium.copy(
                                    color = Color(0xffffffff),
                                    fontWeight = FontWeight.Bold
                                )
                            )

                            Text(
                                text = "AniKun is an unofficial android client for AniList, " +
                                        "in this app you can't read manga or watch anime, but you can " +
                                        "track, share and experience it",
                                style = mTypography.bodyMedium.copy(
                                    color = Color(0xffffffff)
                                ),
                                textAlign = TextAlign.Left
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        if(accessToken == null) {
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFA3C9FE),
                                    contentColor = Color(0xFF00315C)
                                ),
                                onClick = {
                                    webViewEnabled = true
                                },
                                shape = mShapes.small,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = AniKunIcons.AniListColored),
                                        contentDescription = null,
                                        tint = Color.Unspecified
                                    )

                                    Text(
                                        text = "Authenticate with AniList"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        } else {
            AniListAuthPageWebView(
                innerPadding = innerPadding,
                onProgressChange = { pageProgress = it },
                navController = navController
            )
        }
    }
}