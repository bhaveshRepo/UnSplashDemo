package com.example.unsplash.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unsplash.R
import com.example.unsplash.model.ResultData
import kotlinx.android.synthetic.main.item_thumbnail.view.*


class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {


    private val FavoriteDiffCallBack = object: DiffUtil.ItemCallback<ResultData>(){
        override fun areItemsTheSame(oldItem: ResultData, newItem: ResultData): Boolean {
            return oldItem.downloadLink == newItem.downloadLink
        }

        override fun areContentsTheSame(oldItem: ResultData, newItem: ResultData): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,FavoriteDiffCallBack)

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val favoriteView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_thumbnail,parent,false)
        return FavoriteViewHolder(favoriteView)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favoriteItem = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(favoriteItem.imageLink.toString()).into(iv_image)
            setOnClickListener {
                onFavoriteItemClickListener?.let {
                    it(favoriteItem)
                }
            }
        }
    }


    override fun getItemCount(): Int {
        return  differ.currentList.size
    }

    private var onFavoriteItemClickListener : ((ResultData) -> Unit)? = null
    fun setFavoriteItemClickListener(listener : (ResultData) -> Unit){
        onFavoriteItemClickListener = listener
    }

}