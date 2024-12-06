package com.example.zodipair.domain.models

data class RandomUsersResponse(
    val user_id: String,
    val user_name: String,
    val profile_img: String,
    val age: Int,
    val gender: String,
    val target_gender: String,
    val zodiac_symbol: String,
    val description: String,
    val imgs: List<String>,
)
