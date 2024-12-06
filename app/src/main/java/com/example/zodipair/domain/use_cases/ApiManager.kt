package com.example.zodipair.domain.use_cases

import com.example.zodipair.data.network.ApiService
import com.example.zodipair.domain.models.GetProfileModel
import com.example.zodipair.domain.models.GetRandomUsersRequest
import com.example.zodipair.domain.models.GetRequestModel
import com.example.zodipair.domain.models.GetUserModel
import com.example.zodipair.domain.models.RandomUsersResponse
import com.example.zodipair.domain.models.UpdateRequestModel
import com.example.zodipair.domain.models.UpdateRequestResponseModel
import com.example.zodipair.domain.models.UserValidationModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {

    private val retrofit: Retrofit
    val apiService: ApiService
    val baseUrl = "http://201.226.40.22/"

    init {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging) // Agrega logs de las peticiones
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    suspend fun getRandomUsers(count: Int, id: String): List<RandomUsersResponse> {
        return try {
            val request = GetRandomUsersRequest(count, id)
            apiService.getRandomUsers(request)
        } catch (e: Exception) {
            emptyList() // Devuelve una lista vacía en caso de error
        }
    }

    suspend fun postUserValidation(userName: String, password: String): GetUserModel{
         return try{
             val request = UserValidationModel(userName, password)
             apiService.postUserValidation(request)
         }catch (e: Exception){
             GetUserModel(id = "", user_name = "", profile_id = -1, requests_id = -1)
         }
    }

    suspend fun postGetProfile(userId: String): GetProfileModel {
        return try {
            apiService.postGetProfile(userId)
        } catch (e: Exception) {
            GetProfileModel(
                id = -1,
                img = "",
                description = "",
                age = -1,
                gender = "",
                target_gender = "",
                zodiac_symbol = "",
                imgs = null
            )
        }
    }

    suspend fun putAddRequest(userId:String, senderId:String, isHotLove:Boolean): UpdateRequestResponseModel{
        return try {
            val request = UpdateRequestModel(
                user_id = userId,
                sender_id = senderId,
                is_hot_love = isHotLove,
            )
            apiService.putAddRequest(request)
        }catch (e: Exception){
            UpdateRequestResponseModel(status = false)
        }
    }

    suspend fun getUserRequest(userId: String): GetRequestModel{
        return try {
            apiService.getUserRequest(userId)
        }catch (e: Exception){
            GetRequestModel(
                id = -1,
                hearts = emptyList(),
                hot_hearts = emptyList()
            )
        }
    }

    suspend fun getUser(userId: String): GetUserModel{
        return try {
            apiService.getUser(userId)
        }catch (e: Exception){
            GetUserModel(
                id = "",
                user_name = "",
                profile_id = -1,
                requests_id = -1
            )
        }
    }

}
