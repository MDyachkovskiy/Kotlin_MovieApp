package com.example.kotlin_movieapp.model.room.contacts

import androidx.room.*

@Dao
interface ContactsDao {

    @Query("SELECT * FROM ContactsEntity")
    fun all() : List<ContactsEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert (entity: ContactsEntity)

    @Delete
    fun delete (entity: ContactsEntity)
}