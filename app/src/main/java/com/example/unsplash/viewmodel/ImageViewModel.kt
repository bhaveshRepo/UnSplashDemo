package com.example.unsplash.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities.*
import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Email.TYPE_MOBILE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.unsplash.model.UnSplashResponseItem
import com.example.unsplash.repository.ImageRepository
import com.example.unsplash.UnsplashApplication
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
           if(hasInternetConnection()){
               val response = imageRepository.getphotos(pageNumber)
               ImageList.postValue(handleImageResponse(response))
           } else{
               ImageList.postValue(Resource.Error("No Internet Connection"))
           }

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

    private fun hasInternetConnection(): Boolean{
        val connectivityManager = getApplication<UnsplashApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when{
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
            else {
                connectivityManager.activeNetworkInfo?.run {
                    when(type){
                        TYPE_WIFI -> true
                        TYPE_MOBILE -> true
                        TYPE_ETHERNET -> true
                        else -> false
                    }
                }
            }
        return false

    }

}