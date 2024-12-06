package com.example.zodipair.domain.models
import android.os.Parcel
import android.os.Parcelable

@Parcelize
data class GetProfileModel(
    val id: Int,
    val img: String,
    val description: String,
    val age: Int,
    val gender: String,
    val target_gender: String,
    val zodiac_symbol: String,
    val imgs: List<String>?,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.createStringArrayList()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(img)
        parcel.writeString(description)
        parcel.writeInt(age)
        parcel.writeString(gender)
        parcel.writeString(target_gender)
        parcel.writeString(zodiac_symbol)
        parcel.writeStringList(imgs)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GetProfileModel> {
        override fun createFromParcel(parcel: Parcel): GetProfileModel {
            return GetProfileModel(parcel)
        }

        override fun newArray(size: Int): Array<GetProfileModel?> {
            return arrayOfNulls(size)
        }
    }
}
