package com.example.unsplash.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
            val bundle = Bundle().apply {
                putSerializable("favoriteLink",it)
            }
            findNavController().navigate(R.id.action_favoriteFragment_to_openFragment,bundle)
        }

        val touchItemCallBack = object:ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition // get the position of current Element
                val result = favoriteAdapter.differ.currentList[position]
                viewModel.deleteResult(result.id,result.downloadLink,result.imageLink)
                Snackbar.make(view,"Successfully deleted",Snackbar.LENGTH_SHORT).apply {
                    setAction("undo"){
                        viewModel.saveResult(result.id,result.downloadLink,result.imageLink)
                    }
                    show()
                }
            }

        }
        ItemTouchHelper(touchItemCallBack).apply {
            attachToRecyclerView(rvFavorite)
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