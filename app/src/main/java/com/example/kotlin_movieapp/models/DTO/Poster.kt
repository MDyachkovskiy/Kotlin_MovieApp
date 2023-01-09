package com.example.kotlin_movieapp.models.DTO

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Poster(
    val _id: String?,
    val previewUrl: String?,
    val url: String?
) : Parcelable