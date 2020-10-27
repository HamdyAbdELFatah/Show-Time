package com.hamdy.showtime.ui.ui.search.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.SearchFragmentBinding
import com.hamdy.showtime.ui.ui.favorite.adapter.FavoriteViewPagerAdapter
import com.hamdy.showtime.ui.ui.search.adapter.SearchViewPagerAdapter

class SearchFragment : Fragment() {

    private lateinit var binding: SearchFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         binding= SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewPager2: ViewPager2 = binding.viewPagerSearch
        viewPager2.isUserInputEnabled = false
        val adapter = SearchViewPagerAdapter(childFragmentManager, lifecycle)
        viewPager2.adapter = adapter
        val tabLayout =binding.tabLayout
        val names= arrayOf("Movies","Persons")
        TabLayoutMediator(tabLayout, viewPager2){ tab, position ->
            tab.text = names[position]
        }.attach()
    }

}