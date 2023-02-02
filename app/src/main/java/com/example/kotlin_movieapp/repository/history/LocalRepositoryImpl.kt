package com.example.kotlin_movieapp.repository.history

import android.text.Editable
import com.example.kotlin_movieapp.model.movieDetailsResponse.MovieDTO
import com.example.kotlin_movieapp.model.room.history.HistoryDao
import com.example.kotlin_movieapp.model.room.history.HistoryMovieItem
import com.example.kotlin_movieapp.utils.*

class LocalRepositoryImpl (
    private val localDataSource : HistoryDao
        ) : LocalRepository {

    override fun getAllHistory(): List<HistoryMovieItem> {
        return convertListHistoryEntitytoMovie(localDataSource.all())
    }

    override fun saveEntity(movie: MovieDTO, date: Long) {
        val historyEntity = convertMovieDTOToHistoryMovieItem(movie)
        historyEntity.date = date
        localDataSource.insert(convertMovieToEntity(historyEntity))
    }

    override fun addUserComment(movie: MovieDTO, text: Editable?) {
        val dbMovieEntity = convertHistoryEntityToMovie(localDataSource.getDataByMovieName(movie.name))
        dbMovieEntity.userNote = text.toString()
        localDataSource.update(convertMovieToEntity(dbMovieEntity))
    }
}