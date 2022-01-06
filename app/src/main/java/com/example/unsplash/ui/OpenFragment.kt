package com.example.unsplash.ui

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.unsplash.R
import com.example.unsplash.viewmodel.ImageViewModel
import kotlinx.android.synthetic.main.fragment_open.*


class OpenFragment : Fragment(R.layout.fragment_open) {


    lateinit var viewModel: ImageViewModel
    private val args: OpenFragmentArgs by navArgs() // don't now the functionality


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as HostActivity).activityViewModel
        val result = args.searchImage
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(result.urls.full)
        }

    }

}