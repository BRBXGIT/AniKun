package com.example.common.date_functions

import java.time.LocalDateTime

data class Date(
    val year: Int,
    val nextYear: Int,
    val season: String,
    val nextSeason: String
)

fun getDate(): Date {
    val now = LocalDateTime.now()
    val year = now.year
    val nextYear = year + 1
    val season = when(now.monthValue) {
        in 3..5 -> "SPRING"
        in 6..8 -> "SUMMER"
        in 9..11 -> "FALL"
        else -> "WINTER"
    }
    val nextSeason = when(season) {
        "SPRING" -> "SUMMER"
        "SUMMER" -> "FALL"
        "FALL" -> "WINTER"
        else -> "SPRING"
    }
    return Date(
        year = year,
        nextYear = nextYear,
        season = season,
        nextSeason = nextSeason
    )
}