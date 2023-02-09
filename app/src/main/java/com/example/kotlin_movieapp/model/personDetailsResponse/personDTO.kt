package com.example.kotlin_movieapp.model.personDetailsResponse

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PersonDTO(
    val age: Int? = 0,
    val birthPlace: List<BirthPlace>?,
    val birthday: String? = "",
    val id: Int = 0,
    val name: String? = "",
    val photo: String? = "",
    val profession: List<Profession>?,
) : Parcelable