package com.example.unsplash.model.searchresponse

data class UnsplashSearchResponse(
    val results: MutableList<Result>,
    val total: Int,
    val total_pages: Int
)