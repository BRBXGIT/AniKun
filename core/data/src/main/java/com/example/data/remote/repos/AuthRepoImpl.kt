package com.example.data.remote.repos

import com.example.data.local.user_db.AniKunUser
import com.example.data.local.user_db.UserDao
import com.example.data.repos.AuthRepo
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val userDao: UserDao
): AuthRepo {

    override suspend fun upsertUser(user: AniKunUser) {
        userDao.upsertUser(user)
    }
}