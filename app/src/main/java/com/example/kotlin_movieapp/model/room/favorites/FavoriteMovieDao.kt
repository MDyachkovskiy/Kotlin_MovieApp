package com.example.kotlin_movieapp.model.room.favorites

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteMovieDao {

    @Query("SELECT * FROM FavoriteMovieEntity")
    fun all() : List<FavoriteMovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert (entity: FavoriteMovieEntity)

    @Query ("DELETE FROM FavoriteMovieEntity WHERE kinopoiskId = :id")
    fun deleteById(id: Int?)
}