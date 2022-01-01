package com.hamdy.showtime.ui.ui.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.FragmentHomeBinding
import com.hamdy.showtime.ui.ui.home.adapter.HomeCategoryMoviesAdapter
import com.hamdy.showtime.ui.ui.home.adapter.TrendingAdapter
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), View.OnClickListener {
    private val TAG = "HomeFragment"
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val helper: SnapHelper = LinearSnapHelper()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val trendingRecyclerview = binding.trendingRecyclerview


        binding.popularRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.upcomingRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.hightRateRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        helper.attachToRecyclerView(binding.trendingRecyclerview)

        val topRatedAdapter = HomeCategoryMoviesAdapter()
        val adapterTrending = TrendingAdapter()
        val adapterPopular = HomeCategoryMoviesAdapter()
        val upComingAdapter = HomeCategoryMoviesAdapter()

        binding.popularRecyclerView.adapter = adapterPopular
        binding.hightRateRecyclerView.adapter = topRatedAdapter
        binding.upcomingRecyclerView.adapter = upComingAdapter
        trendingRecyclerview.adapter = adapterTrending
        trendingRecyclerview.set3DItem(true)
        trendingRecyclerview.setInfinite(true)
//        trendingRecyclerview.setAlpha(true)
//        trendingRecyclerview.setFlat(true)
        val carouselLayoutManager = trendingRecyclerview.getCarouselLayoutManager()
        val currentlyCenterPosition = trendingRecyclerview.getSelectedPosition()

        homeViewModel.listTrending.observe(viewLifecycleOwner, {
            adapterTrending.setTrends(it)
        })

        homeViewModel.listPopular.observe(viewLifecycleOwner){
            adapterPopular.setMoviesData(it, R.id.action_navigation_home_to_moviesDetails)
        }

        homeViewModel.listTopRated.observe(viewLifecycleOwner, Observer {
            topRatedAdapter.setMoviesData(it, R.id.action_navigation_home_to_moviesDetails)
        })

        homeViewModel.listUpComing.observe(viewLifecycleOwner, Observer {
            upComingAdapter.setMoviesData(it, R.id.action_navigation_home_to_moviesDetails)
        })

        binding.seeAllPopular.setOnClickListener(this)
        binding.seeAllTopRated.setOnClickListener(this)
        binding.seeAllUpComing.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        var bundle = bundleOf("type" to "upComing")
        when (p0?.id) {
            R.id.seeAllPopular -> bundle = bundleOf("type" to "popular")
            R.id.seeAllTopRated -> bundle = bundleOf("type" to "topRate")
        }
        p0?.findNavController()
            ?.navigate(R.id.action_navigation_home_to_fragment_all_movies, bundle, null, null)

    }


}