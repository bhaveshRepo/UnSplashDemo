package com.example.unsplash.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unsplash.model.UnSplashResponseItem
import com.example.unsplash.repository.ImageRepository
import com.example.unsplash.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class ImageViewModel(app: Application, val imageRepository: ImageRepository) : AndroidViewModel(app) {

    val ImageList : MutableLiveData<Resource<List<UnSplashResponseItem>>> = MutableLiveData()
    var pageNumber : Int = 1
    var imageResponse : MutableList<UnSplashResponseItem>? = null

    init {
        getImageResponse()
    }

    fun getImageResponse(){
        viewModelScope.launch {
            safeCallImageResponse()
        }
    }

    private suspend fun safeCallImageResponse(){
       ImageList.postValue(Resource.Loading())
       try{
           val response = imageRepository.getphotos(pageNumber)
           ImageList.postValue(handleImageResponse(response))
       } catch(t : Throwable){
           when(t){
               is IOException -> ImageList.postValue(Resource.Error("Network Failure"))
               else -> {ImageList.postValue(Resource.Error(t.message.toString()))}
           }
       }

    }

    fun handleImageResponse(response: Response<MutableList<UnSplashResponseItem>>) :
            Resource<List<UnSplashResponseItem>>{
        if(response.isSuccessful){
            response.body()?.let {
                pageNumber++
                if(imageResponse == null){
                    imageResponse = it
                } else {
                    val oldArticles  = imageResponse
                    val newArticles =it
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(imageResponse ?: it)
            }
        }
        return Resource.Error(response.message())
    }

}