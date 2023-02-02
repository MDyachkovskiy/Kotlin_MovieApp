package com.example.kotlin_movieapp.model.movieDetailsResponse

import android.os.Parcelable
import com.example.kotlin_movieapp.model.collectionResponse.movieDetailsResponse.SimilarMovy
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDTO(
    val budget: Budget?,
    val countries: List<Country>?,
    val createDate: String?,
    val description: String?,
    val genres: List<Genre>?,
    val id: Int?,
    val type: String?,
    val movieLength: Int?,
    val name: String?,
    val persons: List<Person>,
    val poster: Poster?,
    val rating: Rating?,
    val similarMovies: List<SimilarMovy>?,
    val year: Int?
) : Parcelable