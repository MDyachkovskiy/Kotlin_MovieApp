package com.example.kotlin_movieapp.model.collectionResponse

import com.google.gson.annotations.SerializedName

data class TopTvShowsResponse(
    @SerializedName("docs")
    val topTvShows: List<CollectionItem>
) : Response