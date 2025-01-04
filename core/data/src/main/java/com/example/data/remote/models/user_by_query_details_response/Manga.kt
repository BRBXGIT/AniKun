package com.example.data.remote.models.user_by_query_details_response

import com.example.data.remote.models.manga_list_response.Media as MangaListMedia

data class Manga(
    val nodes: List<MangaListMedia>
)