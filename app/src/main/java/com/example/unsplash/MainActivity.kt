package com.example.unsplash

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unsplash.adapter.ImageAdapter
import com.example.unsplash.repository.ImageRepository
import com.example.unsplash.util.Resource
import com.example.unsplash.viewmodel.ImageViewModel
import com.example.unsplash.viewmodel.ImageViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ImageViewModel
    lateinit var imageAdapter: ImageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = ImageRepository()
        val imageViewModelProvider = ImageViewModelProvider(repository)
        viewModel =ViewModelProvider(this, imageViewModelProvider).get(ImageViewModel::class.java)
        setUpRecyclerView()

        viewModel.ImageList.observe(this, Observer {
         when(it){
             is Resource.Success ->{
                it.data?.let {
                    unSplashResponse -> imageAdapter.differ.submitList(unSplashResponse.toList())
                }
             }
         }

        })
    }

    private fun setUpRecyclerView() {
        imageAdapter = ImageAdapter()
        ivRecyclerView.apply{
            adapter = imageAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}