package com.example.unsplash.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unsplash.model.UnSplashResponse
import com.example.unsplash.model.UnSplashResponseItem
import com.example.unsplash.repository.ImageRepository
import com.example.unsplash.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class ImageViewModel(val imageRepository: ImageRepository) : ViewModel() {

    val ImageList : MutableLiveData<Resource<UnSplashResponse>> = MutableLiveData()
    val pageNumber : Int = 1
    val imageResponse : UnSplashResponseItem? = null

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
           val response = imageRepository.getphotos()
           ImageList.postValue(handleImageResponse(response))
       } catch(t : Throwable){
           ImageList.postValue(Resource.Error("Conversion Error"))
       }

    }

    fun handleImageResponse(response: Response<UnSplashResponse>) :
            Resource<UnSplashResponse>{
        if(response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

}