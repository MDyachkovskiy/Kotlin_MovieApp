package com.example.kotlin_movieapp.model.collectionResponse

import com.google.gson.annotations.SerializedName

data class Top250Response(
    @SerializedName("docs")
    val top250Movies: List<CollectionItem>
) : Response