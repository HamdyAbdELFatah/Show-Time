package com.hamdy.showtime.ui.ui.search.fragment.movie_search.ui

import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.BlurTransformation
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.SearchMoviesFragmentBinding
import com.hamdy.showtime.ui.model.SearchResultsItem
import com.hamdy.showtime.ui.ui.search.adapter.SearchMoviesAdapter
import com.hamdy.showtime.ui.ui.search.ui.SearchFragment
import com.hamdy.showtime.ui.ui.search.ui.SharedViewModel
import com.hamdy.showtime.ui.util.CenterZoomLayoutManager
import com.hamdy.showtime.ui.util.ImageUrlBase
import kotlinx.android.synthetic.main.search_fragment.*

class SearchMoviesFragment : Fragment() {
    private lateinit var viewModel: SearchMoviesViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var binding: SearchMoviesFragmentBinding
    private lateinit var snapHelper: LinearSnapHelper
    private lateinit var arr: List<SearchResultsItem?>
    private var position = 0
    lateinit var dialog: Dialog
    private var firstOpen = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchMoviesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SearchMoviesViewModel::class.java]
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        val layoutManger = CenterZoomLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.searchMoviesRecyclerView.layoutManager = layoutManger
        snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.searchMoviesRecyclerView)
        val searchAdapter = SearchMoviesAdapter()
        binding.searchMoviesRecyclerView.adapter = searchAdapter
        viewModel.listSearchMovie.observe(viewLifecycleOwner, {
            arr = it
            if (arr.isNotEmpty())
                setBackgroundImage(position)
            if(dialog.isShowing)
                dialog.cancel()
            searchAdapter.setQuery(it, R.id.action_searchFragment_to_moviesDetails)
        })
        binding.searchMoviesRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val centerView = snapHelper.findSnapView(layoutManger)
                    val pos: Int = layoutManger.getPosition(centerView!!)
                    setBackgroundImage(pos)

                    Log.e("Snapped Item Position:", "" + pos)
                }
            }
        })

        sharedViewModel.searchQuery.observe(viewLifecycleOwner) {
            val parent = this.parentFragment
            if (it.second == 0 && firstOpen) {
                val query = it.first
                if (query.isEmpty())
                    parent?.searchText?.error = "Query can't be empty"
                else {
                    createDialog()
                    viewModel.getSearchMovies(query)
                }
                Log.d("lol", "onActivityCreated: $query")
            }
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firstOpen = true

    }

    override fun onResume() {
        super.onResume()
        firstOpen = true
    }

    override fun onStop() {
        super.onStop()
        firstOpen = false

    }
    override fun onDestroyView() {
        super.onDestroyView()
        firstOpen = false
    }

    private fun createDialog() {
        dialog = Dialog(requireContext())
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.loading_bar)
        dialog.show()
    }

    private fun setBackgroundImage(position: Int) {
        binding.backGroundFavorite.load(ImageUrlBase + arr[position]?.posterPath) {
            crossfade(true)
            crossfade(1000)
            transformations(BlurTransformation(requireContext(), 25f))
        }
    }
}