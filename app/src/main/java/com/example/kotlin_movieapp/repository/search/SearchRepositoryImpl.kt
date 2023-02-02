package com.example.kotlin_movieapp.repository.search

import com.example.kotlin_movieapp.model.collectionResponse.SearchResponse
import com.example.kotlin_movieapp.repository.RemoteDataSource
import retrofit2.Callback


class SearchRepositoryImpl (
    private val remoteDataSource: RemoteDataSource
) : SearchRepository {

    override fun getAdultLatinSearchResultFromServer(
        rating: Int?,
        name: String,
        callback: Callback<SearchResponse>,
    ) {
        remoteDataSource.getAdultLatinSearchCollection(rating, name, callback)
    }

    override fun getLatinSearchResultFromServer(
        rating: Int?,
        name: String,
        callback: Callback<SearchResponse>,
    ) {
        remoteDataSource.getLatinSearchCollection(rating, name, callback)
    }

    override fun getAdultCyrillicSearchResultFromServer(
        rating: Int?,
        name: String,
        callback: Callback<SearchResponse>,
    ) {
        remoteDataSource.getAdultCyrillicSearchCollection(rating, name, callback)
    }

    override fun getCyrillicSearchResultFromServer(
        rating: Int?,
        name: String,
        callback: Callback<SearchResponse>,
    ) {
        remoteDataSource.getCyrillicSearchCollection(rating, name, callback)
    }


}