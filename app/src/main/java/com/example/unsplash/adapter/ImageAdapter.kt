package com.example.unsplash.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unsplash.R
import com.example.unsplash.model.randomresponse.UnSplashResponseItem
import com.example.unsplash.model.randomresponse.Urls
import kotlinx.android.synthetic.main.item_thumbnail.view.*

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<UnSplashResponseItem>() {
        override fun areItemsTheSame(
            oldItem: UnSplashResponseItem,
            newItem: UnSplashResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: UnSplashResponseItem,
            newItem: UnSplashResponseItem
        ): Boolean {
            return oldItem == newItem
        }


    }

    val differ = AsyncListDiffer(this, diffCallBack)
//    var unsSplashResponseItem : List<UnSplashResponseItem>
//        get() = differ.currentList
//        set(value) {differ.submitList(value)}

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val singleView = LayoutInflater.from(parent.context).inflate(R.layout.item_thumbnail,
        parent, false)

        return ImageViewHolder(singleView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
//        val imageItem =unsSplashResponseItem[position]
        val imageItem = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(imageItem.urls.regular).into(iv_image)
            setOnClickListener {
                onRandomItemClickListener?.let {
                    it(imageItem)
                }

//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(imageItem.urls.full))
//                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onRandomItemClickListener : ((UnSplashResponseItem) -> Unit)? = null

    fun setOnItemClickListener(listener :(UnSplashResponseItem) -> Unit){
        onRandomItemClickListener = listener
    }


}