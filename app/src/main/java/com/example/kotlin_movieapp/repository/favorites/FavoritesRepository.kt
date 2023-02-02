package com.example.kotlin_movieapp.repository.favorites

import com.example.kotlin_movieapp.model.movieDetailsResponse.MovieDTO
import com.example.kotlin_movieapp.model.room.favorites.FavoriteMovieItem

interface FavoritesRepository {
    fun getAllFavorites() : List<FavoriteMovieItem>
    fun saveEntity(movie : MovieDTO)
    fun deleteEntity(movie : MovieDTO)
}