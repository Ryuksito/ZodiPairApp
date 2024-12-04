package com.example.zodipair.data

import com.example.zodipair.domain.models.GetProfileModel
import com.example.zodipair.domain.models.GetUserModel
import com.example.zodipair.domain.models.UserModel

object UserSessionManager {

    var uuid: String? = null
    var user_name: String? = null
    var requests_id: Int? = null
    var profile_id: Int? = null
    var profile_img: String? = null
    var description: String? = null
    var age: Int? = null
    var gender: String? = null
    var target_gender: String? = null
    var zodiac_symbol: String? = null
    var imgs: List<String>? = null

    // Método para configurar los datos de usuario
    fun setUserSession(user: GetUserModel) {
        user_name = user.user_name
        uuid = user.id
        profile_id = user.profile_id
        requests_id = user.requests_id
    }

    fun setUserProfile(profile: GetProfileModel){
        profile_id = profile.id
        description = profile.description
        age = profile.age
        gender = profile.gender
        target_gender = profile.target_gender
        zodiac_symbol = profile.zodiac_symbol
        imgs = profile.imgs
    }

    // Método para limpiar los datos de sesión
    fun clearSession() {
        uuid = null
        user_name = null
        profile_id = null
        profile_img = null
        description = null
        age = null
        gender = null
        target_gender = null
        zodiac_symbol = null
        imgs = null
    }
}
