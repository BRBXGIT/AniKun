package com.example.media_screen.media_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.error_section.ErrorSection
import com.example.designsystem.theme.mColors

@Composable
fun MediaDetailsScreen(
    mediaId: Int,
    viewModel: MediaScreenVM
) {
    //Get and collect media details
    viewModel.fetchMediaDetailsById(mediaId)
    val mediaDetails = viewModel.mediaDetails.collectAsStateWithLifecycle().value

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.background)
    ) { innerPadding ->
        if((mediaDetails.data == null) && (mediaDetails.exception != null)) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        if(mediaDetails.exception != null) {
            ErrorSection(
                errorText = mediaDetails.exception.toString(),
                modifier = Modifier.fillMaxSize()
            )
        }

        if(mediaDetails.data != null) {
            mediaDetails.data!!.media.let { media ->

            }
        }
    }
}