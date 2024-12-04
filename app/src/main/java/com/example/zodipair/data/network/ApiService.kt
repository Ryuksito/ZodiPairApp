package com.example.zodipair.data.network

import com.example.zodipair.domain.models.GetProfileModel
import com.example.zodipair.domain.models.GetRandomUsersRequest
import com.example.zodipair.domain.models.GetUserModel
import com.example.zodipair.domain.models.RandomUsersResponse
import com.example.zodipair.domain.models.UserValidationModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("users/images/random")
    suspend fun getRandomUsers(@Body request: GetRandomUsersRequest): List<RandomUsersResponse>

    @POST("users/user-validation")
    suspend fun postUserValidation(@Body request: UserValidationModel): GetUserModel

    @POST("users/get-profile")
    suspend fun postGetProfile(@retrofit2.http.Query("user_id") userId: String): GetProfileModel
}
