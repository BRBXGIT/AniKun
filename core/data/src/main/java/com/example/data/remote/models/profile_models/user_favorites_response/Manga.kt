package com.example.data.remote.models.profile_models.user_favorites_response

import com.example.data.remote.models.manga_list_response.Media as MangaListMedia

data class Manga(
    val nodes: List<MangaListMedia>
)