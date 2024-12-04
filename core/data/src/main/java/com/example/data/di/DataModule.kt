package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.data_store.AppDataStore
import com.example.data.local.user_db.UserDao
import com.example.data.local.user_db.UserDb
import com.example.data.remote.api_instance.AniListApiInstance
import com.example.data.remote.repos.AnimeScreenRepoImpl
import com.example.data.remote.repos.AuthRepoImpl
import com.example.data.remote.repos.CommonRepoImpl
import com.example.data.remote.repos.MangaScreenRepoImpl
import com.example.data.remote.repos.MediaDetailsScreenRepoImpl
import com.example.data.remote.repos.ProfileScreenRepoImpl
import com.example.data.repos.AnimeScreenRepo
import com.example.data.repos.AuthRepo
import com.example.data.repos.CommonRepo
import com.example.data.repos.MangaScreenRepo
import com.example.data.repos.MediaDetailsScreenRepo
import com.example.data.repos.ProfileScreenRepo
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

    @Provides
    @Singleton
    fun provideAuthRepo(userDao: UserDao): AuthRepo {
        return AuthRepoImpl(userDao)
    }

    @Provides
    @Singleton
    fun provideAnimeScreenRepo(apiInstance: AniListApiInstance): AnimeScreenRepo {
        return AnimeScreenRepoImpl(apiInstance)
    }

    @Provides
    @Singleton
    fun provideMangaScreenRepo(apiInstance: AniListApiInstance): MangaScreenRepo {
        return MangaScreenRepoImpl(apiInstance)
    }

    @Provides
    @Singleton
    fun provideProfileScreenRepo(apiInstance: AniListApiInstance): ProfileScreenRepo {
        return ProfileScreenRepoImpl(apiInstance)
    }

    @Provides
    @Singleton
    fun provideCommonRepo(apiInstance: AniListApiInstance, userDao: UserDao): CommonRepo {
        return CommonRepoImpl(apiInstance, userDao)
    }

    @Provides
    @Singleton
    fun provideMediaDetailsScreenRepo(apiInstance: AniListApiInstance): MediaDetailsScreenRepo {
        return MediaDetailsScreenRepoImpl(apiInstance)
    }
}