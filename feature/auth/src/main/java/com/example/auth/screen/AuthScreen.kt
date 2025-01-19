package com.example.auth.screen

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.auth.R
import com.example.designsystem.icons.AniKunIcons
import com.example.designsystem.theme.mColors
import com.example.designsystem.theme.mShapes
import com.example.designsystem.theme.mTypography

@Composable
fun AuthScreen(
    accessToken: String,
    navController: NavController,
    viewModel: AuthScreenVM,
    prefs: SharedPreferences,
    context: Context = LocalContext.current
) {
    LaunchedEffect(accessToken) {
        Log.d("CCCC", accessToken)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.background)
    ) { innerPadding ->
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
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "AniKun",
                    style = mTypography.headlineSmall.copy(
                        color = Color(0xffffffff)
                    )
                )

                Text(
                    text = "An unofficial android client for AniList",
                    style = mTypography.bodyMedium.copy(
                        color = Color(0xffffffff)
                    )
                )
            }

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFA3C9FE),
                    contentColor = Color(0xFF00315C)
                ),
                onClick = {  },
                shape = mShapes.small,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
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