package com.allysonjeronimo.marvelapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.allysonjeronimo.marvelapp.data.db.entity.Comic

@Dao
interface ComicDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(comics:List<Comic>)

    @Query("select * from Comic")
    fun findAll() : List<Comic>
}