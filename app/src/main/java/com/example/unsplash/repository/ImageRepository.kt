package com.example.unsplash.repository

import com.example.unsplash.api.RetrofitInstance
import com.example.unsplash.db.DatabaseInstance
import com.example.unsplash.model.searchresponse.Result

class ImageRepository() {

    suspend fun getphotos(pageNumber: Int) = RetrofitInstance.api.getPhotos(pageNumber)

    suspend fun getSearch(searchQuery: String, pageNumber: Int) = RetrofitInstance.api.searchPhotos(searchQuery, pageNumber)

    //  Database part

//    suspend fun upsert(result: Result) =
//        db.favoriteDao().upsert(result)
//
//    fun saveData() = db.favoriteDao().getAllData()
//
//    suspend fun deleteResult(result: Result) =
//        db.favoriteDao().deleteResult(result)

}