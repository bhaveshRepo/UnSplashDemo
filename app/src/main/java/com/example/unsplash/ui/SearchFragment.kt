package com.example.unsplash.ui

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.unsplash.util.Resource
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplash.R
import com.example.unsplash.adapter.SearchAdapter
import com.example.unsplash.util.Constants
import com.example.unsplash.viewmodel.ImageViewModel
import kotlinx.android.synthetic.main.fragment_random.*
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


        searchAdapter.setOnItemClickListener {
            val bundle = Bundle().apply{
                putSerializable("result",it)
            }
            findNavController().navigate(R.id.action_searchFragment_to_openFragment,bundle)
        }


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
                    hideProgressBar()
                    isLoading = false
                    searchResponse.data?.let {
                        imageResponse -> searchAdapter.differ.submitList(imageResponse.results.toList())

                    }
                } is Resource.Error -> {
                    hideProgressBar()
                    isLoading = false
                    searchResponse.message?.let {
                        Toast.makeText(context,
                        "Error occurred $it",Toast.LENGTH_LONG).show()
                    }
                } is Resource.Loading -> {
                    showProgressBar()
                    isLoading = true
                }
            }
        }
        )


    }

    private fun hideProgressBar(){
        paginationProgressBarSearch.visibility = View.INVISIBLE
    }
    private fun showProgressBar(){
        paginationProgressBarSearch.visibility = View.VISIBLE
    }

    private fun setUpRecyclerView() {
        searchAdapter = SearchAdapter()
        rvSearchNews.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@SearchFragment.scrollListener)
        }
    }


    // logic for pagination

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val  scrollListener = object: RecyclerView.OnScrollListener(){

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleCount = layoutManager.childCount
            val totalItemCount =   layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading || !isLastPage
            val isLastItem = firstVisibleItemPosition + visibleCount >= totalItemCount
            val isNotBeginning  = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage&& isLastItem && isNotBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if(shouldPaginate){
                viewModel.getSearch(etSearch.text.toString())
                isScrolling = false
            }
        }
    }


}