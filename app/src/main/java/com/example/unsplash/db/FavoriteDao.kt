package com.example.unsplash.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.unsplash.model.ResultData
import com.example.unsplash.model.searchresponse.Result

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(resultData: ResultData): Long


    @Query("SELECT * from resultData")
    fun getAllData() : LiveData<List<ResultData>>

    @Delete
    suspend fun deleteResult(resultData: ResultData)

}