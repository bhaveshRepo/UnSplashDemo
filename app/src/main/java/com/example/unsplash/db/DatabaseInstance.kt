package com.example.unsplash.db

import android.content.Context
import androidx.room.*
import com.example.unsplash.model.searchresponse.Result

@Database(
    entities = [Result::class],
    version = 1
)
@TypeConverters(Converter::class)
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
                "result_db.db").build()

    }
}