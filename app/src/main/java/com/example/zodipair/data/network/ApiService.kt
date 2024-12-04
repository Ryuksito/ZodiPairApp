package com.example.zodipair.data.network

import com.example.zodipair.domain.models.LoginRequest
import com.example.zodipair.domain.models.LoginResponse
import com.example.zodipair.domain.models.UserImageResponse
import com.example.zodipair.domain.models.UserResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Path

interface ApiService {

    @GET("users/images/random/{count}")
    suspend fun getRandomUsers(@Path("count") count: Int): List<UserImageResponse>

}
