package com.example.kotlin_movieapp.repository.collections

import com.example.kotlin_movieapp.model.collectionResponse.Top250Response
import com.example.kotlin_movieapp.model.collectionResponse.TopTvShowsResponse
import com.example.kotlin_movieapp.model.collectionResponse.UpComingResponse
import com.example.kotlin_movieapp.repository.RemoteDataSource
import retrofit2.Callback

class CollectionsRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : CollectionsRepository {

    override fun getTop250CollectionFromServer(callback: Callback<Top250Response>) {
        remoteDataSource.getTop250Collection(callback)

    }

    override fun getTopTvShowsCollectionFromServer(callback: Callback<TopTvShowsResponse>) {
        remoteDataSource.getTopTvShowsCollection(callback)
    }

    override fun getUpComingCollectionFromServer(callback: Callback<UpComingResponse>) {
        remoteDataSource.getUpComingCollection(callback)
    }


}