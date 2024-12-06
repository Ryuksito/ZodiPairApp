package com.example.zodipair.data

import com.example.zodipair.domain.models.GetProfileModel
import com.example.zodipair.domain.models.GetUserModel

object UserSessionManager {

    var uuid: String? = null
        private set // Evita que se genere un setter público

    var user_name: String? = null
        private set

    var password: String? = null
        private set

    var requests_id: Int? = null
        private set

    var profile_id: Int? = null
        private set

    var profile_img: String? = null
        private set

    var description: String? = null
        private set

    var age: Int? = null
        private set

    var gender: String? = null
        private set

    var target_gender: String? = null
        private set

    var zodiac_symbol: String? = null
        private set

    var imgs: List<String>? = null
        private set

    // Método para configurar los datos de usuario
    fun setUserSession(user: GetUserModel) {
        user_name = user.user_name
        uuid = user.id
        profile_id = user.profile_id
        requests_id = user.requests_id
    }

    fun setUserProfile(profile: GetProfileModel) {
        profile_id = profile.id
        description = profile.description
        profile_img = profile.img
        age = profile.age
        gender = profile.gender
        target_gender = profile.target_gender
        zodiac_symbol = profile.zodiac_symbol
        imgs = profile.imgs
    }

    // Métodos específicos para actualizar variables individuales
    fun updateUuid(newUuid: String?) {
        uuid = newUuid
    }

    fun updateUserName(newUserName: String?) {
        user_name = newUserName
    }

    fun updatePassword(newPassword: String?) {
        password = newPassword
    }

    fun updateRequestsId(newRequestsId: Int?) {
        requests_id = newRequestsId
    }

    fun updateProfileId(newProfileId: Int?) {
        profile_id = newProfileId
    }

    fun updateProfileImg(newProfileImg: String?) {
        profile_img = newProfileImg
    }

    fun updateDescription(newDescription: String?) {
        description = newDescription
    }

    fun updateAge(newAge: Int?) {
        age = newAge
    }

    fun updateGender(newGender: String?) {
        gender = newGender
    }

    fun updateTargetGender(newTargetGender: String?) {
        target_gender = newTargetGender
    }

    fun updateZodiacSymbol(newZodiacSymbol: String?) {
        zodiac_symbol = newZodiacSymbol
    }

    // Método para limpiar los datos de sesión
    fun clearSession() {
        uuid = null
        user_name = null
        password = null
        requests_id = null
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
