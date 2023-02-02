package com.example.kotlin_movieapp.model.personDetailsResponse

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Profession(
    val value: String? = ""
) : Parcelable