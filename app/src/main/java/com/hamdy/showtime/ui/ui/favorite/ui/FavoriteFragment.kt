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
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.FragmentFavoriteBinding
import com.hamdy.showtime.ui.ui.favorite.adapter.FavoriteAdapter
import com.hamdy.showtime.ui.ui.favorite.adapter.FavoriteViewPagerAdapter
import com.hamdy.showtime.ui.ui.favorite.ui.fragment.favorite_movies.ui.FavoriteMoviesFragment
import com.hamdy.showtime.ui.ui.favorite.ui.fragment.favorite_person.ui.FavoritePersonFragment
import com.hamdy.showtime.ui.ui.movies.ui.AllMoviesViewModel
import com.hamdy.showtime.ui.util.CenterZoomLayoutManager

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewPager2WithFragments()
    }


    private fun initViewPager2WithFragments() {
        val viewPager2: ViewPager2 = binding.viewPagerFavorite
        val adapter = FavoriteViewPagerAdapter(childFragmentManager, lifecycle)
        viewPager2.adapter = adapter
    }

}