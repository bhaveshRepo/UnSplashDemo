package com.example.unsplash.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.unsplash.HostActivity
import com.example.unsplash.R
import com.example.unsplash.viewmodel.ImageViewModel
import kotlinx.android.synthetic.main.fragment_open.*


class OpenFragment : Fragment(R.layout.fragment_open) {


    lateinit var viewModel: ImageViewModel
    val args : OpenFragmentArgs by navArgs()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as HostActivity).hostViewModel
        val webUrl = args.webLink

//        webView.apply { // to open articular link in a webView
//          webViewClient = WebViewClient()
//            loadUrl(webUrl.regular)
//        }


//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webUrl.regular))
//        startActivity(intent)

        Glide.with(this).load(webUrl.full).into(webView)




    }



}