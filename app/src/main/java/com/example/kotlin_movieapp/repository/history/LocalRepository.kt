package com.example.kotlin_movieapp.repository.history

import android.text.Editable
import com.example.kotlin_movieapp.model.movieDetailsResponse.MovieDTO
import com.example.kotlin_movieapp.model.room.history.HistoryMovieItem

interface LocalRepository {
    fun getAllHistory() : List<HistoryMovieItem>
    fun saveEntity(movie : MovieDTO, date: Long)
    fun addUserComment(movie : MovieDTO, text: Editable?)
}