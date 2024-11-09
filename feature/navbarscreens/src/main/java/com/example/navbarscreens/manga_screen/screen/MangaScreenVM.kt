package com.example.navbarscreens.manga_screen.screen

import androidx.lifecycle.ViewModel
import com.example.data.remote.repos.CommonRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MangaScreenVM @Inject constructor(
    commonRepository: CommonRepoImpl
): ViewModel() {
}