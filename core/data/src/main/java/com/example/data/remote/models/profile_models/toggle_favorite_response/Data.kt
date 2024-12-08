package com.example.data.remote.models.profile_models.toggle_favorite_response

import com.example.data.remote.models.profile_models.user_favorites_response.Favourites
import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("ToggleFavourite")
    val toggleFavourite: Favourites
)