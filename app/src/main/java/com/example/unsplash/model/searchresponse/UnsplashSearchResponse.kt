package com.example.unsplash.model.searchresponse

import java.io.Serializable

data class UnsplashSearchResponse(
    val results: MutableList<Result>,
    val total: Int,
    val total_pages: Int
): Serializable