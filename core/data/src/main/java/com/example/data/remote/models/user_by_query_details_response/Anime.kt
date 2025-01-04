package com.example.data.remote.models.user_by_query_details_response

import com.example.data.remote.models.anime_list_response.Media as AnimeListMedia

data class Anime(
    val nodes: List<AnimeListMedia>
)