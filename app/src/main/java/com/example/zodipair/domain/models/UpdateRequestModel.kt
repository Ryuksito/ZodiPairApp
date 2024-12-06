package com.example.zodipair.domain.models

data class UpdateRequestModel(
    val user_id: String,
    val sender_id: String,
    val is_hot_love: Boolean,
)
