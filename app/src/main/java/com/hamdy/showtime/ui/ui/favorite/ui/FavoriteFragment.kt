package com.hamdy.showtime.ui.ui.favorite.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.FragmentFavoriteBinding
import com.hamdy.showtime.ui.ui.favorite.adapter.FavoriteAdapter
import com.hamdy.showtime.ui.ui.movies.ui.AllMoviesViewModel
import com.hamdy.showtime.ui.util.CenterZoomLayoutManager

class FavoriteFragment : Fragment() {
    private lateinit var favoriteViewModel: AllMoviesViewModel
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var snapHelper: LinearSnapHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        favoriteViewModel = ViewModelProvider(this).get(AllMoviesViewModel::class.java)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val layoutManger=CenterZoomLayoutManager(context, RecyclerView.HORIZONTAL, false)
        super.onActivityCreated(savedInstanceState)
        val arr= listOf(R.drawable.test,R.drawable.test2,R.drawable.test3,R.drawable.test4,R.drawable.test,R.drawable.test2)
        binding.favoriteRecyclerView.layoutManager=layoutManger
        binding.favoriteRecyclerView.adapter=FavoriteAdapter()
        snapHelper =LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.favoriteRecyclerView)

        binding.favoriteRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val centerView = snapHelper.findSnapView(layoutManger)
                    val pos: Int = layoutManger.getPosition(centerView!!)
                    binding.backGroundFavorite.load(arr[pos]){
                        crossfade(true)
                        crossfade(1000)
                    }
                    Log.e("Snapped Item Position:", "" + pos)
                }
            }
        })
    }

}