package com.example.kotlin_movieapp.model.collectionResponse.movieDetailsResponse

import android.os.Parcelable
import com.example.kotlin_movieapp.model.movieDetailsResponse.Poster
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SimilarMovy(
    val _id: String?,
    val alternativeName: String?,
    val enName: String?,
    val id: Int?,
    val name: String?,
    val poster: Poster?
) : Parcelable