package com.example.data.repos

import com.example.data.remote.models.user_by_query_details_response.UserByQueryDetailsResponse

interface UserScreenRepo {

    suspend fun getUserByQueryDetails(userName: String): UserByQueryDetailsResponse
}