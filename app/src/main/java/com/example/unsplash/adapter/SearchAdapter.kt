package com.example.unsplash.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unsplash.R
import com.example.unsplash.model.searchresponse.Result
import kotlinx.android.synthetic.main.item_thumbnail.view.*

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
                onItemClickListenerSearch?.let {
                    it(searchItem)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListenerSearch : ((Result) -> Unit)? = null

    fun setOnItemClickListener(listener : (Result) -> Unit){
        onItemClickListenerSearch  = listener
    }

}