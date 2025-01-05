package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.data_store.AppDataStore
import com.example.data.local.user_db.UserDao
import com.example.data.local.user_db.UserDb
import com.example.data.remote.api_instance.AniListApiInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideAniListApi(): AniListApiInstance {
        return Retrofit.Builder()
            .baseUrl("https://graphql.anilist.co")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideUserDao(@ApplicationContext context: Context): UserDao {
        return Room.databaseBuilder(
            context = context,
            klass = UserDb::class.java,
            name = "UserDb"
        ).build().userDao()
    }

    @Provides
    @Singleton
    fun provideThemeDataStore(@ApplicationContext context: Context): AppDataStore {
        return AppDataStore(context)
    }
}