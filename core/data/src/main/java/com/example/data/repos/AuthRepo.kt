package com.example.data.repos

import com.example.data.local.user_db.AniKunUser

interface AuthRepo {

    suspend fun upsertUser(user: AniKunUser)
}