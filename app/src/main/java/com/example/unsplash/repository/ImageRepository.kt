package com.example.unsplash.repository

import com.example.unsplash.api.RetrofitInstance
import com.example.unsplash.db.DatabaseInstance
import com.example.unsplash.model.ResultData
import com.example.unsplash.model.searchresponse.Result

class ImageRepository(val db: DatabaseInstance) {

    suspend fun getphotos(pageNumber: Int) = RetrofitInstance.api.getPhotos(pageNumber)

    suspend fun getSearch(searchQuery: String, pageNumber: Int) = RetrofitInstance.api.searchPhotos(searchQuery, pageNumber)

    //  Database part

    suspend fun upsert(id:String, link: String, url:String) =
        db.favoriteDao().upsert(ResultData(id,link,url))

    fun saveData() = db.favoriteDao().getAllData()

    suspend fun deleteResult(id:String, link: String, url:String) =
        db.favoriteDao().deleteResult(ResultData(id,link,url))

}