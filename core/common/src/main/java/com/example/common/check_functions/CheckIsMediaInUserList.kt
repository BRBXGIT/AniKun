package com.example.common.check_functions

import com.example.data.remote.models.profile_models.user_favorites_response.Favourites
import com.example.data.remote.models.profile_models.user_anime_list_response.Lists as UserAnimeLists
import com.example.data.remote.models.profile_models.user_manga_list_response.Lists as UserMangaLists

fun checkIsMediaInUserList(
    userMangaLists: List<UserMangaLists>,
    userAnimeLists: List<UserAnimeLists>,
    mediaId: Int
): String {
    userAnimeLists.forEach { list ->
        list.entries.forEach { entry ->
            if(entry.media.id == mediaId) return list.name
        }
    }

    userMangaLists.forEach { list ->
        list.entries.forEach { entry ->
            if(entry.media.id == mediaId) return list.name
        }
    }

    return "Not in list"
}

fun checkIsAnimeInUserLists(
    userAnimeLists: List<UserAnimeLists>?,
    mediaId: Int
): String? {
    userAnimeLists?.forEach { list ->
        list.entries.forEach { entry ->
            if(entry.media.id == mediaId) return list.name
        }
    }

    return null
}

fun checkIsMediaInFavorites(
    userFavorites: Favourites,
    mediaId: Int
): Boolean {
    userFavorites.anime.nodes.forEach { anime ->
        if(mediaId == anime.id) return true
    }
    userFavorites.manga.nodes.forEach { manga ->
        if(mediaId == manga.id) return true
    }

    return false
}