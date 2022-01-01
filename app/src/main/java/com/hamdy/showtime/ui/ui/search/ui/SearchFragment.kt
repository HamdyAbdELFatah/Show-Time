package com.hamdy.showtime.ui.ui.search.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.SearchFragmentBinding
import com.hamdy.showtime.ui.ui.search.adapter.SearchViewPagerAdapter
import com.hamdy.showtime.ui.ui.search.fragment.movie_search.ui.SearchMoviesFragment
import com.hamdy.showtime.ui.ui.search.fragment.movie_search.ui.SearchMoviesViewModel


class SearchFragment : Fragment() {

    private lateinit var binding: SearchFragmentBinding
    private lateinit var viewPager2: ViewPager2
    private lateinit var viewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        viewPager2 = binding.viewPagerSearch
        viewPager2.isUserInputEnabled = false
        val adapter = SearchViewPagerAdapter(childFragmentManager, lifecycle)
        viewPager2.adapter = adapter
        val tabLayout = binding.tabLayout
        val names = arrayOf("Movies", "Persons")
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = names[position]
        }.attach()

        binding.searchIcon.setOnClickListener {
            viewModel.getSearchMovies(
                binding.searchText.text.toString(),binding.tabLayout.selectedTabPosition

            )
        }
    }

}