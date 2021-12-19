package com.example.unsplash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.unsplash.repository.ImageRepository

class ImageViewModelProvider(val imageRepository: ImageRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ImageViewModel(imageRepository) as T
    }
}