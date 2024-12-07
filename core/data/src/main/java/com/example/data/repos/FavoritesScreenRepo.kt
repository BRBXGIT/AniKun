package com.example.data.repos

import com.example.data.remote.models.profile_models.user_favorites_response.UserFavoritesResponse

interface FavoritesScreenRepo {

    suspend fun getUserFavorites(userName: String): UserFavoritesResponse
}