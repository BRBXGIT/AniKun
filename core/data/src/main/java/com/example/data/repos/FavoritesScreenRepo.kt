package com.example.data.repos

import com.example.data.remote.models.profile_models.toggle_favorite_response.ToggleFavoriteResponse
import com.example.data.remote.models.profile_models.user_favorites_response.UserFavoritesResponse

interface FavoritesScreenRepo {

    suspend fun getUserFavorites(userName: String): UserFavoritesResponse

    suspend fun toggleFavorite(
        animeId: Int,
        mangaId: Int,
        characterId: Int,
        mediaType: String,
        accessToken: String
    ): ToggleFavoriteResponse
}