package com.hamdy.showtime.ui.ui.search.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hamdy.showtime.ui.ui.favorite.ui.fragment.favorite_movies.ui.FavoriteMoviesFragment
import com.hamdy.showtime.ui.ui.favorite.ui.fragment.favorite_person.ui.FavoritePersonFragment
import com.hamdy.showtime.ui.ui.search.fragment.movie_search.ui.SearchMoviesFragment
import com.hamdy.showtime.ui.ui.search.fragment.person_search.ui.SearchPersonFragment

class SearchViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragments:ArrayList<Fragment> = arrayListOf(
        SearchMoviesFragment(),
        SearchPersonFragment()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}