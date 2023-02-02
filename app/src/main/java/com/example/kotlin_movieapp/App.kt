package com.example.kotlin_movieapp

import android.app.Application
import androidx.room.Room
import com.example.kotlin_movieapp.model.room.history.HistoryDao
import com.example.kotlin_movieapp.model.room.HistoryDataBase
import com.example.kotlin_movieapp.model.room.contacts.ContactsDao
import com.example.kotlin_movieapp.model.room.favorites.FavoriteMovieDao

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        appInstance = this
    }

    companion object{
        private var appInstance: App? = null
        private var db : HistoryDataBase? = null
        private const val DB_NAME = "History.db"

        fun getHistoryDao() : HistoryDao {
            synchronized(HistoryDataBase::class.java) {
                if (db == null) {
                    if (appInstance == null) throw IllegalStateException("APP must not be null")

                    db = Room.databaseBuilder(appInstance!!.applicationContext,
                    HistoryDataBase::class.java,
                    DB_NAME).build()
                }
            }
            return db!!.historyDao()
        }

        fun getFavoriteDao() : FavoriteMovieDao {
            synchronized(HistoryDataBase::class.java) {
                if(db == null) {
                    if (appInstance == null) throw IllegalStateException("APP must not be null")

                    db = Room.databaseBuilder(appInstance!!.applicationContext,
                    HistoryDataBase::class.java,
                    "Favorites.db").build()
                }
            }
            return db!!.favoriteDao()
        }

        fun getContactsDao() : ContactsDao {
            synchronized(HistoryDataBase::class.java){
                if (db == null) {
                    if (appInstance == null) throw IllegalStateException("APP must not be null")

                    db = Room.databaseBuilder(appInstance!!.applicationContext,
                    HistoryDataBase::class.java,
                    "Contacts.db").build()
                }
            }
            return db!!.contactsDao()
        }
    }
}