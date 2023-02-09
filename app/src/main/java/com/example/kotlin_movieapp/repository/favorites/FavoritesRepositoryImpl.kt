package com.example.kotlin_movieapp.repository.favorites

import com.example.kotlin_movieapp.model.movieDetailsResponse.MovieDTO
import com.example.kotlin_movieapp.model.room.favorites.FavoriteMovieDao
import com.example.kotlin_movieapp.model.room.favorites.FavoriteMovieItem
import com.example.kotlin_movieapp.utils.*

class FavoritesRepositoryImpl (
    private val localDataSource : FavoriteMovieDao
        ) : FavoritesRepository {

    override fun getAllFavorites(): List<FavoriteMovieItem> {
        return convertListFavoritesEntityToMovie(localDataSource.all())
    }

    override fun saveEntity(movie: MovieDTO) {
        val favoriteEntity = convertMovieDTOtoFavoriteMovieEntity(movie)
        localDataSource.insert(favoriteEntity)
    }

    override fun deleteEntity(movie: MovieDTO) {
        localDataSource.deleteById(movie.id)
    }
}