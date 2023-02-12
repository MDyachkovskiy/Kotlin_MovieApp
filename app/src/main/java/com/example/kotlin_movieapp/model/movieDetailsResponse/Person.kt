package com.example.kotlin_movieapp.model.movieDetailsResponse

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Person(
    val enName: String? = "",
    val enProfession: String? = "",
    val id: Int = 0,
    val name: String? = "",
    val photo: String? = ""
) : Parcelable