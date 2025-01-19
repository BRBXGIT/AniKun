package com.example.auth.screen

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.auth.utils.AuthUtils
import com.example.designsystem.theme.mColors
import com.example.designsystem.theme.mShapes
import com.example.navbarscreens.common.navigation.NavBarScreensRoute

@Composable
fun AuthScreen(
    accessToken: String,
    navController: NavController,
    viewModel: AuthScreenVM,
    prefs: SharedPreferences,
    context: Context = LocalContext.current
) {
    Log.d("CCCC", accessToken)

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
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Button(
                    onClick = {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://anilist.co/api/v2/oauth/authorize?client_id=${AuthUtils.CLIENT_ID}&response_type=token")
                            )
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
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1
                )

                Button(
                    onClick = {
                        if(accessToken.isNotBlank()) {
                            prefs.edit().apply {
                                putBoolean("loggedIn", true)
                                apply()
                            }
                            viewModel.upsertUser(accessToken)
                            navController.navigate(NavBarScreensRoute)
                        }
                    },
                    shape = mShapes.small,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Authorize")
                }
            }
        }
    }
}