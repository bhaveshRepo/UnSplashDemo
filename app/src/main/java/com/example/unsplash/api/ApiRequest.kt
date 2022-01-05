package com.example.unsplash.api

import com.example.unsplash.model.randomresponse.UnSplashResponseItem
import com.example.unsplash.model.searchresponse.UnsplashSearchResponse
import com.example.unsplash.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequest {

    // get request for getting random photos
    @GET("photos")
    suspend fun getPhotos(
        @Query("page")
        page : Int = 1,
        @Query("client_id")
        client_id : String = Constants.access_key
    ) : Response<MutableList<UnSplashResponseItem>>


    // get request for search query
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query")
        query: String = "office",
        @Query("page")
        page: Int = 1,
        @Query("client_id")
        client_id: String = Constants.access_key,
    ) : Response<UnsplashSearchResponse>

}