package com.example.data.local.user_db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Upsert
    suspend fun upsertUser(user: AniKunUser)

    @Delete
    suspend fun deleteUser(user: AniKunUser)

    @Query("SELECT * FROM anikunuser")
    fun getUser(): Flow<List<AniKunUser>>
}