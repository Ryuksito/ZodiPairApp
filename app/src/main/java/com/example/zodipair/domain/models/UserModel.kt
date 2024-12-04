package com.example.zodipair.domain.models

data class UserModel(
    val uuid: String,
    val user_name: String,
    val profile_id: Int,
    val requests_id: Int,
    val profile_img: String,
    val description: String,
    val age: Int,
    val gender: String,
    val target_gender: String,
    val zodiac_symbol: String,
    val imgs: List<String>
)
