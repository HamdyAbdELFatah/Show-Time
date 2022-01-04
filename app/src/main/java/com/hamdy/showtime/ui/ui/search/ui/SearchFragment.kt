package com.hamdy.showtime.ui.ui.search.ui

import android.graphics.Color
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.FrameLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.SearchFragmentBinding
import com.hamdy.showtime.ui.ui.search.adapter.SearchViewPagerAdapter
import com.hamdy.showtime.ui.ui.search.fragment.movie_search.ui.SearchMoviesFragment
import com.hamdy.showtime.ui.ui.search.fragment.movie_search.ui.SearchMoviesViewModel





class SearchFragment : Fragment() {
    private val TAG = "SearchFragment"
    private lateinit var binding: SearchFragmentBinding
    private lateinit var viewPager2: ViewPager2
    private lateinit var viewModel: SharedViewModel

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
        removeStatusBarTransparent()
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
    private fun removeStatusBarTransparent(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = requireActivity().window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.colorPrimary)
        }
        val root : ConstraintLayout? = activity?.findViewById(R.id.root)
        if (root != null) {
            ViewCompat.setOnApplyWindowInsetsListener(root) { view, windowInsets ->
                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
                view.layoutParams =  (view.layoutParams as FrameLayout.LayoutParams).apply {
                    topMargin = insets.top
                    leftMargin = insets.left
                    bottomMargin = insets.bottom
                    rightMargin = insets.right
                }
                WindowInsetsCompat.CONSUMED
            }
        }
    }
    private fun addStatusBarTransparent(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = requireActivity().window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.transparent)

        }
        WindowCompat.setDecorFitsSystemWindows(activity?.window!!, false)
        val root : ConstraintLayout? = activity?.findViewById(R.id.root)
        if (root != null) {
            ViewCompat.setOnApplyWindowInsetsListener(root) { view, windowInsets ->
                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
                view.layoutParams =  (view.layoutParams as FrameLayout.LayoutParams).apply {
                    topMargin = 0
                    leftMargin = insets.left
                    bottomMargin = insets.bottom
                    rightMargin = insets.right
                }
                WindowInsetsCompat.CONSUMED
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")

        addStatusBarTransparent()
    }
}