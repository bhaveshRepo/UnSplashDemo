package com.example.unsplash.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unsplash.HostActivity
import com.example.unsplash.R
import com.example.unsplash.adapter.FavoriteAdapter
import com.example.unsplash.viewmodel.ImageViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_favorite.*


class FavoriteFragment : Fragment() {

lateinit var viewModel: ImageViewModel
lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as HostActivity).hostViewModel
        setUpFavoriteRecyclerView()

        favoriteAdapter.setFavoriteItemClickListener {
                resultresponse ->
            Snackbar.make(view,"Choose",Snackbar.LENGTH_LONG).apply {
//                setAction("open"){
//                    val bundle = Bundle().apply {
//                        putSerializable("favoriteLink",resultresponse)
//                    }
//                    findNavController().navigate(R.id.action_favoriteFragment_to_openFragment,bundle)
//                }
//                show()
                setAction("delete"){
                    viewModel.deleteResult(resultresponse.id,resultresponse.downloadLink,resultresponse.imageLink)
                    Snackbar.make(view,"Bring back",Snackbar.LENGTH_SHORT).apply {
                        setAction("Undo"){
                            viewModel.saveResult(resultresponse.id,
                                resultresponse.downloadLink,
                                resultresponse.imageLink)
                        }
                        show()
                    }
                }
                show()
            }


        }



        viewModel.getSavedImage().observe(viewLifecycleOwner, Observer {
            favoriteAdapter.differ.submitList(it)
        }
        )

    }

    private fun setUpFavoriteRecyclerView() {
        favoriteAdapter = FavoriteAdapter()
        rvFavorite.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}