package com.allysonjeronimo.marvelapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.allysonjeronimo.marvelapp.data.db.dao.ComicDAO
import com.allysonjeronimo.marvelapp.data.db.dao.ShoppingCartItemDAO
import com.allysonjeronimo.marvelapp.data.db.entity.Comic
import com.allysonjeronimo.marvelapp.data.db.entity.ShoppingCartItem

@Database(
    entities = [Comic::class, ShoppingCartItem::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase(){

    abstract fun ComicDAO() : ComicDAO
    abstract fun ShoppingCartItemDAO() : ShoppingCartItemDAO

    companion object{

        @Volatile
        private var INSTANCE:AppDatabase? = null
        private const val DB_NAME = "marvel-db"

        fun getInstance(context: Context) : AppDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        DB_NAME
                    ).build()
                }
                return instance
            }
        }
    }
}