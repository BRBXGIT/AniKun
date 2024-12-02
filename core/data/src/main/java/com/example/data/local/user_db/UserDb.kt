package com.example.data.local.user_db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [AniKunUser::class],
    version = 1
)
abstract class UserDb: RoomDatabase() {

    abstract fun userDao(): UserDao
}