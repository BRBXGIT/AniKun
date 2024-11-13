package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AniKunUser(
    @PrimaryKey
    val accessToken: String
)
