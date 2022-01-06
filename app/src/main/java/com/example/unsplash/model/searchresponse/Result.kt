package com.example.unsplash.model.searchresponse

import java.io.Serializable

data class Result(
    val id: String,
    val links: Links,
    val urls: UrlsX,
) : Serializable