package com.example.auth.sections

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.auth.utils.AuthUtils

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun AniListAuthPageWebView(
    innerPadding: PaddingValues
) {
    val context = LocalContext.current
    val url by rememberSaveable { mutableStateOf("https://anilist.co/api/v2/oauth/authorize?client_id=${AuthUtils.CLIENT_ID}&response_type=token") }
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    val webView = remember {
        android.webkit.WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            settings.javaScriptEnabled = true
            webViewClient = android.webkit.WebViewClient()

            loadUrl(url)
        }
    }

    DisposableEffect(webView) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(webView.canGoBack()) {
                    webView.goBack()
                }
            }
        }
        onBackPressedDispatcher?.addCallback(callback)

        onDispose { callback.remove() }
    }

    AndroidView(
        factory = { webView },
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    )
}