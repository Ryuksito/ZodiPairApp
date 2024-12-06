package com.example.zodipair.domain.models

data class GetRequestModel(
    val id: Int,
    val hearts: List<String>,
    val hot_hearts: List<String>,
)
