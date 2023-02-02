package com.example.kotlin_movieapp.repository.movieDetails

import com.example.kotlin_movieapp.model.movieDetailsResponse.MovieDTO
import com.example.kotlin_movieapp.repository.RemoteDataSource
import retrofit2.Callback


class DetailsRepositoryImpl (
    private val remoteDataSource: RemoteDataSource
) : DetailsRepository {

    override fun getMovieDetailsFromServer(movieId: Int?, callback: Callback<MovieDTO>) {
        remoteDataSource.getMovieDetails(movieId, callback)
    }
}