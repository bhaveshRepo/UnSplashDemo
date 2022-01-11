package com.example.unsplash.model.searchTesting

data class unsplashSearch(
    val results: List<Result>,
    val total: Int,
    val total_pages: Int
)