package com.hamdy.showtime.ui.ui.movies.ui

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.FragmentAllMoviesBinding
import com.hamdy.showtime.ui.ui.home.adapter.HomeCategoryMoviesAdapter
import com.hamdy.showtime.ui.util.MyItemDecoration

class AllMoviesFragment : Fragment() {
    private val TAG = "AllMoviesFragment"
    private lateinit var allMoviesViewModel: AllMoviesViewModel
    private lateinit var binding: FragmentAllMoviesBinding
    private lateinit var type: String
    private var canGetPopular = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        allMoviesViewModel =
            ViewModelProvider(this)[AllMoviesViewModel::class.java]
        type = arguments?.get("type").toString()
        allMoviesViewModel.myStart(type, createDialog())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onActivityCreated: Called")

        val adapter = HomeCategoryMoviesAdapter()
        binding.allMoviesRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.allMoviesRecyclerView.addItemDecoration(MyItemDecoration(requireContext()))
        binding.allMoviesRecyclerView.adapter = adapter

        allMoviesViewModel.listMovies.observe(viewLifecycleOwner, {
            adapter.setMoviesData(it, R.id.action_fragment_all_movies_to_moviesDetails)
            binding.loadingMoreProgressBar.visibility = View.GONE
            canGetPopular = true
        })

        binding.allMoviesRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) { //1 for down
                    if (canGetPopular) {
                        when (type) {
                            "popular" -> {
                                allMoviesViewModel.getPopular()
                            }
                            "topRate" -> {
                                allMoviesViewModel.getTopRated()
                            }
                            else -> {
                                allMoviesViewModel.getUpComing()
                            }
                        }
                        binding.loadingMoreProgressBar.visibility = View.VISIBLE
                        canGetPopular = false
                    }
                }
            }
        })
    }

    private fun createDialog(): Dialog {
        val dialog = Dialog(requireContext())
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.loading_bar)
        dialog.show()
        return dialog
    }
}