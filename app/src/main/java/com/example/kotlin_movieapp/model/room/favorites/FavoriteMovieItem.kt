package com.example.kotlin_movieapp.model.room.favorites

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteMovieItem (
    var kinopoiskId: Int? = 0,
    var name: String? = "",
    var description: String? = "",
    var poster: String? = "",
    var date: Long = 0,
    var userNote: String? = "",
    var isFavorite: Boolean = false
) : Parcelable