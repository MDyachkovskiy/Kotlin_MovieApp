package com.example.kotlin_movieapp.model.collectionResponse

import android.os.Parcelable
import com.example.kotlin_movieapp.model.movieDetailsResponse.Poster
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CollectionItem(
    @SerializedName("id")
    val kinopoiskId: Int? = 0,
    var poster: Poster? = Poster(),
    val name: String? = "",
    val description: String? = ""
) : Parcelable
