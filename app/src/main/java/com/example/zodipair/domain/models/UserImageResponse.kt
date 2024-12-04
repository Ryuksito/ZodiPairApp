package com.example.zodipair.domain.models

data class UserImageResponse(
    val user_name: String,     // Nombre del usuario
    val fileName: String, // Nombre del la imagen
    val url_image: String  // URL de la imagen del usuario
)
