package com.example.data.di

import com.example.data.remote.api_instance.AniListApiInstance
import com.example.data.remote.repos.AnimeScreenRepoImpl
import com.example.data.repos.AnimeScreenRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideAnimeScreenRepo(apiInstance: AniListApiInstance): AnimeScreenRepo {
        return AnimeScreenRepoImpl(apiInstance)
    }
}