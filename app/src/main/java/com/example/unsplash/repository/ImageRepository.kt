package com.example.unsplash.repository

import com.example.unsplash.api.RetrofitInstance

class ImageRepository {

    suspend fun getphotos(pageNumber: Int) = RetrofitInstance.api.getPhotos(pageNumber)

    suspend fun getSearch(searchQuery: String, pageNumber: Int) = RetrofitInstance.api.searchPhotos(searchQuery, pageNumber)

}