package com.allysonjeronimo.marvelapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.allysonjeronimo.marvelapp.data.db.entity.Comic

@Dao
interface ComicDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(comics:List<Comic>)

    @Query("select * from Comic order by id ASC")
    suspend fun findAll() : List<Comic>

    @Query("select * from Comic where id = :id")
    suspend fun find(id:Int) : Comic?

    @Query("select count(id) from Comic")
    suspend fun count() : Int
}