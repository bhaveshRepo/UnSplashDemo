package com.example.unsplash.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.unsplash.HostActivity
import com.example.unsplash.R
import com.example.unsplash.viewmodel.ImageViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_open.*


class OpenFragment : Fragment(R.layout.fragment_open) {


    lateinit var viewModel: ImageViewModel
    val args : OpenFragmentArgs by navArgs()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as HostActivity).hostViewModel


        // recieving data without safeArgs
//        val searchUrl = arguments?.getString("searchLink")
//        val downloadUrl = arguments?.getString("downloadLink")

        //recieving data with the help of SafeArgs

        val searchUrl = args.searchLink
        val randomUrl = args.randomLink
        val favoriteUrl = args.favoriteLink

        if (searchUrl != null) {
            Glide.with(this).load(searchUrl.urls.regular).into(webView)
            fab.setOnClickListener {
//                CustomTabsIntent.Builder().build().launchUrl(requireContext(),
//                    searchUrl.links.download.toUri())
            viewModel.saveResult(searchUrl.id,searchUrl.links.download,searchUrl.urls.regular.toString())
                Snackbar.make(view,"Image Saved",Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo"){
                        viewModel.deleteResult(searchUrl.id,searchUrl.links.download,searchUrl.urls.regular.toString())
                        Snackbar.make(view,"Deleted",Snackbar.LENGTH_SHORT).show()
                    }
                    show()
                }

            }
        }
        if(randomUrl != null){
            Glide.with(this).load(randomUrl.urls.regular).into(webView)
            fab.setOnClickListener {
//                CustomTabsIntent.Builder().build().launchUrl(requireContext(),
//                    randomUrl.links.download.toUri())
                viewModel.saveResult(randomUrl.id,randomUrl.links.download,randomUrl.urls.regular)
                Snackbar.make(view,"Image Saved",Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo"){
                        viewModel.deleteResult(randomUrl.id,randomUrl.links.download,randomUrl.urls.regular.toString())
                        Snackbar.make(view,"Deleted",Snackbar.LENGTH_SHORT).show()
                    }
                    show()
                }
            }
        }
        if(favoriteUrl != null){
            Glide.with(this).load(favoriteUrl.imageLink).into(webView)
            fab.setOnClickListener {
                Snackbar.make(view,"Already in Favorites",Snackbar.LENGTH_SHORT).show()
            }
        }






    }



}