package com.example.unsplash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController // for version < androidx.appcompat:appcompat:1.3.1
import androidx.navigation.ui.setupWithNavController
import com.example.unsplash.repository.ImageRepository
import com.example.unsplash.viewmodel.ImageViewModel
import com.example.unsplash.viewmodel.ImageViewModelProvider
import kotlinx.android.synthetic.main.activity_host.*

class HostActivity : AppCompatActivity(R.layout.activity_host) {

    lateinit var hostViewModel : ImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = ImageRepository()
        val viewModelProvider = ImageViewModelProvider(application, repository)
        hostViewModel = ViewModelProvider(this, viewModelProvider).get(ImageViewModel::class.java)
//        bottomNavigationView.setupWithNavController(imageNavHostFragment.findNavController())
        // if you are using androidx.appcompat:appcompat:1.3.1 or lesser version


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.imageNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)



    }
}