package com.example.unsplash.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.unsplash.R
import com.example.unsplash.adapter.ImageAdapter
import com.example.unsplash.repository.ImageRepository
import com.example.unsplash.viewmodel.ImageViewModel
import com.example.unsplash.viewmodel.ImageViewModelProvider
import kotlinx.android.synthetic.main.activity_host.*

class HostActivity : AppCompatActivity(R.layout.activity_host) {

    lateinit var activityViewModel : ImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = ImageRepository()
        val viewModelProvider = ImageViewModelProvider(application, repository)
        activityViewModel = ViewModelProvider(this, viewModelProvider).get(ImageViewModel::class.java)
        bottomNavigationView.setupWithNavController(imageNavHostFragment.findNavController())

    }
}