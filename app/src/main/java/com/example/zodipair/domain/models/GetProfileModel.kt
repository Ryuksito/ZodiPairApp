package com.example.zodipair.domain.models

data class GetProfileModel(
    val id: Int,
    val img: String,
    val description: String,
    val age: Int,
    val gender: String,
    val target_gender: String,
    val zodiac_symbol: String,
    val imgs: List<String>,
)
