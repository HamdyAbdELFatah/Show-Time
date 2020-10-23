package com.hamdy.showtime.ui.ui.favorite.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hamdy.showtime.ui.ui.favorite.ui.fragment.favorite_movies.ui.FavoriteMoviesFragment
import com.hamdy.showtime.ui.ui.favorite.ui.fragment.favorite_person.ui.FavoritePersonFragment

class FavoriteViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragments:ArrayList<Fragment> = arrayListOf(
        FavoriteMoviesFragment(),
        FavoritePersonFragment()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}