package com.example.unsplash.model.searchresponse

import java.io.Serializable

data class UrlsX(
    val full: String,
    val raw: String,
    val regular: String,
    val small: String,
    val thumb: String
) : Serializable