package com.example.auth.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.data.local.AniKunUser
import com.example.designsystem.theme.mColors
import com.example.designsystem.theme.mShapes

@Composable
fun AuthScreen(
    navController: NavController,
    viewModel: AuthScreenVM
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.background)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
        ) {
            Button(
                onClick = {
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://anilist.co/api/v2/oauth/authorize?client_id=22328&redirect_uri=null&response_type=code")
                    )
                },
                shape = mShapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Get token")
            }

            var accessToken by rememberSaveable { mutableStateOf("") }
            OutlinedTextField(
                value = accessToken,
                onValueChange = { accessToken = it },
                label = { Text("Access token") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.upsertUser(AniKunUser(accessToken))
                },
                shape = mShapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Authorize")
            }
        }
    }
}