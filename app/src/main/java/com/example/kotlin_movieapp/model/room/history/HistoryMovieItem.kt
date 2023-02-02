package com.example.kotlin_movieapp.model.room.history

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HistoryMovieItem (
    var kinopoiskId: Int? = 0,
    var name: String? = "",
    var description: String? = "",
    var poster: String? = "",
    var date: Long = 0,
    var userNote: String? = ""
) : Parcelable