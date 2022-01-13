package com.example.unsplash.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "resultData"
)
data class ResultData(

    @PrimaryKey
    val id: String,
    val downloadLink: String,
    val imageLink: String,

) : Serializable