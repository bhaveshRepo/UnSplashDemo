package com.example.unsplash.api

import com.example.unsplash.model.UnSplashResponseItem
import com.example.unsplash.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequest {

    @GET("photos")
    suspend fun getPhotos(
        @Query("page")
        page : Int = 1,
        @Query("client_id")
        client_id : String = Constants.access_key
    ) : Response<MutableList<UnSplashResponseItem>>


}