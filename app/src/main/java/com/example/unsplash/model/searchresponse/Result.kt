package com.example.unsplash.model.searchresponse

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "favorites"
)
data class Result(
    @PrimaryKey
    val id: String,
    val links: Links,
    val urls: UrlsX,

) : Serializable