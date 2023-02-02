package com.example.kotlin_movieapp.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kotlin_movieapp.model.room.contacts.ContactsDao
import com.example.kotlin_movieapp.model.room.contacts.ContactsEntity
import com.example.kotlin_movieapp.model.room.favorites.FavoriteMovieDao
import com.example.kotlin_movieapp.model.room.favorites.FavoriteMovieEntity
import com.example.kotlin_movieapp.model.room.history.HistoryDao
import com.example.kotlin_movieapp.model.room.history.HistoryEntity

@Database(version = 1,
    exportSchema = false,
    entities = [
    HistoryEntity ::class,
    FavoriteMovieEntity ::class,
    ContactsEntity ::class
    ]
)
abstract class HistoryDataBase : RoomDatabase() {
    abstract fun historyDao() : HistoryDao
    abstract fun favoriteDao() : FavoriteMovieDao
    abstract fun contactsDao() : ContactsDao
}