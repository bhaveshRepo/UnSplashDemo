package com.example.unsplash.model.searchresponse

import java.io.Serializable

data class Links(
    val download: String,
    val download_location: String,
) : Serializable