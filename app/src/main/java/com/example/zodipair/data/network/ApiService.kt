package com.example.zodipair.data.network

import com.example.zodipair.domain.models.GetProfileModel
import com.example.zodipair.domain.models.GetRandomUsersRequest
import com.example.zodipair.domain.models.GetRequestModel
import com.example.zodipair.domain.models.GetUserModel
import com.example.zodipair.domain.models.RandomUsersResponse
import com.example.zodipair.domain.models.UpdateRequestModel
import com.example.zodipair.domain.models.UpdateRequestResponseModel
import com.example.zodipair.domain.models.UserValidationModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("users/images/random")
    suspend fun getRandomUsers(@Body request: GetRandomUsersRequest): List<RandomUsersResponse>

    @POST("users/user-validation")
    suspend fun postUserValidation(@Body request: UserValidationModel): GetUserModel

    @POST("users/get-profile")
    suspend fun postGetProfile(@retrofit2.http.Query("user_id") userId: String): GetProfileModel

    @PUT("users/requests/add")
    suspend fun putAddRequest(@Body request: UpdateRequestModel): UpdateRequestResponseModel

    @GET("users/requests/get-user-request")
    suspend fun getUserRequest(@Query("user_id") userId: String): GetRequestModel

    @GET("users/user")
    suspend fun getUser(@Query("user_id") userId: String): GetUserModel
}
