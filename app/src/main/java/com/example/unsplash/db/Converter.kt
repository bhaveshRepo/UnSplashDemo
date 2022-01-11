package com.example.unsplash.db

import androidx.room.TypeConverter
import com.example.unsplash.model.searchresponse.Links
import com.example.unsplash.model.searchresponse.UrlsX

class Converter {

    @TypeConverter
    fun fromLink(link: Links) : String{
        return link.download
    }

    @TypeConverter
    fun toLink(name: String): Links{
        return Links(name,name)
    }

    @TypeConverter
    fun fromUrls(url: UrlsX) : String{
        return url.regular.toString()
    }

    @TypeConverter
    fun toUrls(saveUrl: String) : UrlsX{
        return UrlsX(saveUrl,saveUrl)
    }


}