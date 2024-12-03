package com.example.zodipair.domain.models

data class UserImageResponse(
    val name: String,     // Nombre del usuario
    val fileName: String, // Nombre del la imagen
    val imageUrl: String  // URL de la imagen del usuario
)
