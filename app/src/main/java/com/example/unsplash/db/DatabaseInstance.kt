package com.example.unsplash.db

import android.content.Context
import androidx.room.*
import com.example.unsplash.model.ResultData
import com.example.unsplash.model.searchresponse.Result

@Database(
    entities = [ResultData::class],
    version = 3
)
//@TypeConverters(Converter::class)
abstract class DatabaseInstance : RoomDatabase(){

    abstract fun favoriteDao(): FavoriteDao

    companion object{

        private var INSTANCE : DatabaseInstance? = null

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(this){
            INSTANCE ?: createDatabase(context).also {
                instance -> INSTANCE = instance
            }

        }
        private fun createDatabase(context: Context) =
             Room.databaseBuilder(context.applicationContext,
                DatabaseInstance::class.java,
                "resultData1_db.db").build()

    }
}