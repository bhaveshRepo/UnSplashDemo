package com.example.unsplash.model.randomresponse

import java.io.Serializable

data class UnSplashResponseItem (

    val id: String?,
    val updated_at: String?,
    val urls: Urls,
    val width: Int?
) : Serializable