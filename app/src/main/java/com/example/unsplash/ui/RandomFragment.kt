package com.example.unsplash.ui

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplash.R
import com.example.unsplash.adapter.ImageAdapter
import com.example.unsplash.util.Constants
import com.example.unsplash.util.Resource
import com.example.unsplash.viewmodel.ImageViewModel
import kotlinx.android.synthetic.main.fragment_random.*


class RandomFragment : Fragment(R.layout.fragment_random) {

    lateinit var viewModel: ImageViewModel
    lateinit var imageAdapter: ImageAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = (activity as HostActivity).activityViewModel
        setUpRecyclerView()

        viewModel.ImageList.observe(viewLifecycleOwner,Observer{
            when(it){
                is Resource.Success -> {
                    hideProgressBar()
                    isLoading = false
                    it.data?.let {
                        imageList -> imageAdapter.differ.submitList(imageList)
                    }
                } is Resource.Error ->{
                    hideProgressBar()
                    isLoading = false
                    it.message?.let {
                        ErrorMessage -> Toast.makeText(context,"Error $ErrorMessage",Toast.LENGTH_LONG).show()
                    }
                } is Resource.Loading -> {
                isLoading = true
                    showProgressBar()
                }
            }
        })


    }

    private fun hideProgressBar(){
        paginationProgressBar.visibility = View.INVISIBLE
    }
    private fun showProgressBar(){
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setUpRecyclerView() {
        imageAdapter = ImageAdapter()
        ivRecyclerView.apply {
            adapter = imageAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@RandomFragment.scrollListener)
        }
    }

    // logic for pagination

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


}