package com.example.data.repos

import com.example.data.local.AniKunUser

interface AuthRepo {

    suspend fun upsertUser(user: AniKunUser)
}