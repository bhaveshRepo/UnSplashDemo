package com.example.unsplash.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.unsplash.util.Resource
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unsplash.R
import com.example.unsplash.adapter.SearchAdapter
import com.example.unsplash.viewmodel.ImageViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var viewModel: ImageViewModel
    lateinit var searchAdapter: SearchAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as HostActivity).activityViewModel
        setUpRecyclerView()

        var job : Job? = null
        etSearch.addTextChangedListener{ editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                editable?.let{
                    if(editable.toString().isNotEmpty()){
                        viewModel.getSearch(editable.toString())
                    }
            }
            }
        }


        viewModel.SearchList.observe(viewLifecycleOwner, Observer{
            searchResponse -> when(searchResponse){
                is Resource.Success ->{
                    searchResponse.data?.let {
                        imageResponse -> searchAdapter.differ.submitList(imageResponse.results.toList())

                    }
                } is Resource.Error -> {
                    searchResponse.message?.let {
                        Toast.makeText(context,
                        "Error occurred $it",Toast.LENGTH_LONG).show()
                    }
                } is Resource.Loading -> {}
            }
        }
        )


    }

    private fun setUpRecyclerView() {
        searchAdapter = SearchAdapter()
        rvSearchNews.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


}