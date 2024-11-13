package com.example.data.repos

import com.example.data.local.AniKunUser
import kotlinx.coroutines.flow.Flow

interface AuthRepo {

    suspend fun upsertUser(user: AniKunUser)

    fun getUser(): Flow<List<AniKunUser>>
}