package com.example.unsplash

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplash.adapter.ImageAdapter
import com.example.unsplash.model.UnSplashResponseItem
import com.example.unsplash.repository.ImageRepository
import com.example.unsplash.util.Constants
import com.example.unsplash.util.Resource
import com.example.unsplash.viewmodel.ImageViewModel
import com.example.unsplash.viewmodel.ImageViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ImageViewModel
    lateinit var imageAdapter: ImageAdapter

    private val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = ImageRepository()
        val imageViewModelProvider = ImageViewModelProvider(repository)
        viewModel =ViewModelProvider(this, imageViewModelProvider).get(ImageViewModel::class.java)
        setUpRecyclerView()

        viewModel.ImageList.observe(this){
            when(it){
                is Resource.Success -> {
                    paginationProgressBar.visibility = View.INVISIBLE
                    isLoading = false
                    it.data?.let {
                        unResponse -> imageAdapter.differ.submitList(unResponse.toList())
                    }
                } is Resource.Error ->{
                    paginationProgressBar.visibility = View.INVISIBLE
                isLoading = false
                    it.message?.let {
                        Toast.makeText(this,"Error : $it",Toast.LENGTH_LONG)
                        Log.e(TAG, it)
                    }
                } is Resource.Loading ->{
                    paginationProgressBar.visibility = View.VISIBLE
                isLoading = true
                }
            }
        }
    }

    var isLoading = false
    var isScrolling = false

    var scrollListener  = object : RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            var visibleItemCount = layoutManager.childCount
            var totalItemCount = layoutManager.itemCount


            val isNotLoadingAndNotLastPage = !isLoading
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            if(shouldPaginate){
                viewModel.getImageResponse()
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }

        }
    }

    private fun setUpRecyclerView() {
        imageAdapter = ImageAdapter()
        ivRecyclerView.apply{
            adapter = imageAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addOnScrollListener(this@MainActivity.scrollListener)
        }
    }
}