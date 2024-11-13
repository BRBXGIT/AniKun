package com.example.data.remote.repos

import com.example.data.local.AniKunUser
import com.example.data.local.UserDao
import com.example.data.repos.AuthRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val userDao: UserDao
): AuthRepo {

    override suspend fun upsertUser(user: AniKunUser) {
        userDao.upsertUser(user)
    }

    override fun getUser(): Flow<List<AniKunUser>> {
        return userDao.getUser()
    }
}