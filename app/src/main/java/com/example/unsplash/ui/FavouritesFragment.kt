package com.example.unsplash.ui

import android.os.Bundle
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unsplash.HostActivity
import com.example.unsplash.R
import com.example.unsplash.adapter.SearchAdapter
import com.example.unsplash.viewmodel.ImageViewModel
import kotlinx.android.synthetic.main.fragment_favourites.*


class FavouritesFragment : Fragment(R.layout.fragment_favourites) {

//    lateinit var viewModel: ImageViewModel
//    lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        viewModel = (activity as HostActivity).hostViewModel
//        setUpRecyclerView()
//
//        viewModel.getSavedImage().observe(viewLifecycleOwner, Observer{
//            searchAdapter.differ.submitList(it)
//        })
//
//    }
//
//    private fun setUpRecyclerView() {
//        searchAdapter = SearchAdapter()
//        rvSavedNews.apply {
//            adapter = searchAdapter
//            layoutManager= LinearLayoutManager(activity)
//        }
//    }


    }
}