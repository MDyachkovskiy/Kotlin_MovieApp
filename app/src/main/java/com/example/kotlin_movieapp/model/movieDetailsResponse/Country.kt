package com.example.kotlin_movieapp.model.movieDetailsResponse

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Country(
    val name: String?
) : Parcelable