package com.example.kotlin_movieapp.model.room.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface HistoryDao {

    @Query("SELECT * FROM HistoryEntity")
    fun all() : List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE name LIKE :name")
    fun getDataByMovieName (name: String?) : HistoryEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert (entity: HistoryEntity)

    @Update
    fun update (entity: HistoryEntity)
}