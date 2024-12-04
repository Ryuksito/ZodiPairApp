package com.example.zodipair.domain.use_cases

import com.example.zodipair.data.network.ApiService
import com.example.zodipair.domain.models.UserImageResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
            .baseUrl("http://201.226.182.199/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    suspend fun getRandomUsers(count: Int): List<UserImageResponse> {
        return try {
            apiService.getRandomUsers(count)
        } catch (e: Exception) {
            emptyList()  // Devuelve una lista vacía en caso de error
        }
    }

}
