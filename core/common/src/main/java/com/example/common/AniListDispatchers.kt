package com.example.common

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val aniListDispatcher: AniListDispatchers)

enum class AniListDispatchers {
    Default,
    IO,
}