package com.example.kotlin_movieapp.repository.collections

import com.example.kotlin_movieapp.model.collectionResponse.Top250Response
import com.example.kotlin_movieapp.model.collectionResponse.TopTvShowsResponse
import com.example.kotlin_movieapp.model.collectionResponse.UpComingResponse

interface CollectionsRepository {
    fun getTop250CollectionFromServer(callback : retrofit2.Callback<Top250Response>)
    fun getTopTvShowsCollectionFromServer(callback : retrofit2.Callback<TopTvShowsResponse>)
    fun getUpComingCollectionFromServer(callback: retrofit2.Callback<UpComingResponse>)
}