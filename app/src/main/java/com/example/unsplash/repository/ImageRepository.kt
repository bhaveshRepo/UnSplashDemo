package com.example.unsplash.repository

import com.example.unsplash.api.RetrofitInstance

class ImageRepository {

    suspend fun getphotos() = RetrofitInstance.api.getPhotos()

}