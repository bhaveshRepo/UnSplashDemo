package com.example.unsplash.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unsplash.R
import com.example.unsplash.model.searchresponse.Result
import com.example.unsplash.model.searchresponse.UrlsX
import kotlinx.android.synthetic.main.fragment_open.view.*
import kotlinx.android.synthetic.main.fragment_random.view.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.item_thumbnail.view.*
import java.util.ArrayList

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {


    private val searchDifferCallBack = object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,searchDifferCallBack)



    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_thumbnail,parent,false)

        return SearchViewHolder(layoutInflater)
    }



    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

        val searchItem = differ.currentList[position]
        holder.itemView.apply{
            Glide.with(this).load(searchItem.urls.regular).into(iv_image)

            setOnClickListener {
                    onSearchItemClickListener?.let {
                        it(searchItem)
                    }
                }
            }
        }



    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onSearchItemClickListener : ((Result) -> Unit)? = null

    fun setOnItemClickListener(listener : (Result) -> Unit){
        onSearchItemClickListener = listener
    }


//    private var onSearchItemClickListener : ((UrlsX) -> Unit)? = null
//
//    fun setOnItemClickListener(listener : (UrlsX) -> Unit){
//        onSearchItemClickListener = listener
//    }

}