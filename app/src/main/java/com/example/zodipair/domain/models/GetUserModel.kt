package com.example.zodipair.domain.models

data class GetUserModel(
    val id: String,
    val user_name: String,
    val profile_id: Int,
    val requests_id: Int,
)
