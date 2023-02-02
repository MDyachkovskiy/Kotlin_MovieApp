package com.example.kotlin_movieapp.model.collectionResponse

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchResponse(
    @SerializedName("docs")
    val searchResults: List<CollectionItem>
) : Parcelable