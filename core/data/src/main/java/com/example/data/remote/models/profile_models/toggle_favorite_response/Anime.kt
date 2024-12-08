package com.example.data.remote.models.profile_models.toggle_favorite_response

import com.example.data.remote.models.anime_list_response.Media as AnimeListMedia

data class Anime(
    val nodes: List<AnimeListMedia>
)