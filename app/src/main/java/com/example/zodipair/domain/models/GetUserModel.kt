package com.example.zodipair.domain.models
import android.os.Parcel
import android.os.Parcelable

@Parcelize
data class GetUserModel(
    val id: String,
    val user_name: String,
    val profile_id: Int,
    val requests_id: Int,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(user_name)
        parcel.writeInt(profile_id)
        parcel.writeInt(requests_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GetUserModel> {
        override fun createFromParcel(parcel: Parcel): GetUserModel {
            return GetUserModel(parcel)
        }

        override fun newArray(size: Int): Array<GetUserModel?> {
            return arrayOfNulls(size)
        }
    }
}

annotation class Parcelize
