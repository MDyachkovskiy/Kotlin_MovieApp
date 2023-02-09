package com.example.kotlin_movieapp.repository.movieDetails

import com.example.kotlin_movieapp.model.movieDetailsResponse.MovieDTO

interface DetailsRepository {
    fun getMovieDetailsFromServer(movieId: Int?, callback: retrofit2.Callback<MovieDTO>)
}