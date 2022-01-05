package com.example.unsplash.model.searchresponse

data class UnsplashSearchResponse(
    val results: List<Result>,
    val total: Int,
    val total_pages: Int
)