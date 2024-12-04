package com.example.zodipair.domain.use_cases

import com.example.zodipair.data.network.ApiService
import com.example.zodipair.domain.models.GetProfileModel
import com.example.zodipair.domain.models.GetRandomUsersRequest
import com.example.zodipair.domain.models.GetUserModel
import com.example.zodipair.domain.models.RandomUsersResponse
import com.example.zodipair.domain.models.UserModel
import com.example.zodipair.domain.models.UserValidationModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body

class ApiManager {

    public var ip: String = "201.226.182.199"
    private val retrofit: Retrofit
    val apiService: ApiService

    init {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging) // Agrega logs de las peticiones
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl("http://$ip/")
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
                imgs = emptyList()
            )
        }
    }


}
