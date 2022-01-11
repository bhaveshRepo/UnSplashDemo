package com.example.unsplash.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.unsplash.model.searchresponse.Result

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(result: Result): Long


    @Query("SELECT * from favorites")
    fun getAllData() : LiveData<List<Result>>

    @Delete
    suspend fun deleteResult(result: Result)

}