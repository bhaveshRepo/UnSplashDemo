package com.example.unsplash.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.unsplash.repository.ImageRepository

class ImageViewModelProvider(val app: Application,val imageRepository: ImageRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ImageViewModel(app,imageRepository) as T
    }
}