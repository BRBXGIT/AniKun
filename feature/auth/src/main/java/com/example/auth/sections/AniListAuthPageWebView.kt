@file:Suppress("DEPRECATION")

package com.example.auth.sections

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.CookieManager
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.auth.navigation.AuthScreenRoute
import com.example.auth.utils.AuthUtils

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun AniListAuthPageWebView(
    innerPadding: PaddingValues,
    onProgressChange: (Int) -> Unit,
    navController: NavController
) {
    val context = LocalContext.current
    val initialUrl = "https://anilist.co/api/v2/oauth/authorize?client_id=${AuthUtils.CLIENT_ID}&response_type=token"
    var urlState by rememberSaveable { mutableStateOf(initialUrl) }

    val webView = remember {
        android.webkit.WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.databaseEnabled = true
            settings.setSupportMultipleWindows(true)
            settings.allowContentAccess = true
            settings.allowFileAccess = true
            settings.allowFileAccessFromFileURLs = true
            settings.allowUniversalAccessFromFileURLs = true

            //OAuth needs cookies
            CookieManager.getInstance().setAcceptCookie(true)
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)

            webViewClient = object : android.webkit.WebViewClient() {
                override fun onPageFinished(view: android.webkit.WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    urlState = url ?: ""
                }

                override fun doUpdateVisitedHistory(view: android.webkit.WebView?, url: String?, isReload: Boolean) {
                    super.doUpdateVisitedHistory(view, url, isReload)
                    urlState = url ?: ""
                }
            }

            webChromeClient = object : android.webkit.WebChromeClient() {
                override fun onProgressChanged(view: android.webkit.WebView?, newProgress: Int) {
                    onProgressChange(newProgress)
                }
            }

            loadUrl(urlState)
        }
    }

    LaunchedEffect(urlState) {
        if(urlState.split("#")[0] == "https://ani-kun.com/") {
            navController.navigate(
                AuthScreenRoute(
                    accessToken = urlState.split("#")[1]
                )
            )
        }
    }

    AndroidView(
        factory = { webView },
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    )
}