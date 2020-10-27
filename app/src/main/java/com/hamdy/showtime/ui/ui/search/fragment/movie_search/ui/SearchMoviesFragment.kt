package com.hamdy.showtime.ui.ui.search.fragment.movie_search.ui

import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.SearchMoviesFragmentBinding
import com.hamdy.showtime.ui.model.SearchResultsItem
import com.hamdy.showtime.ui.ui.search.adapter.SearchAdapter
import com.hamdy.showtime.ui.ui.search.ui.SearchFragment
import com.hamdy.showtime.ui.util.CenterZoomLayoutManager
import com.hamdy.showtime.ui.util.ImageUrlBase
import kotlinx.android.synthetic.main.search_fragment.*

class SearchMoviesFragment : Fragment() {
    private lateinit var viewModel: SearchMoviesViewModel
    private lateinit var binding: SearchMoviesFragmentBinding
    private lateinit var snapHelper: LinearSnapHelper
    private lateinit var arr: List<SearchResultsItem?>
    private var position =0
    lateinit var dialog: Dialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = SearchMoviesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchMoviesViewModel::class.java)
        val layoutManger= CenterZoomLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.searchMoviesRecyclerView.layoutManager=layoutManger
        snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.searchMoviesRecyclerView)
        val searchAdapter = SearchAdapter()
        binding.searchMoviesRecyclerView.adapter = searchAdapter
        viewModel.listSearchMovie.observe(viewLifecycleOwner, {
            arr=it
            if(arr.isNotEmpty())
                binding.backGroundFavorite.load(ImageUrlBase +arr[position]?.posterPath){
                    crossfade(true)
                    crossfade(1000)
                }
            dialog.cancel()
            searchAdapter.setQuery(it,R.id.action_searchFragment_to_moviesDetails)
        })
        binding.searchMoviesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val centerView = snapHelper.findSnapView(layoutManger)
                    val pos: Int = layoutManger.getPosition(centerView!!)
                    position=pos
                    binding.backGroundFavorite.load(ImageUrlBase +arr[pos]?.posterPath){
                        crossfade(true)
                        crossfade(1000)
                    }
                    Log.e("Snapped Item Position:", "" + pos)
                }
            }
        })

        val parent= (parentFragment as SearchFragment)
        parent.searchIcon.setOnClickListener{
            val query=parent.searchText.text
            if(query.isEmpty())
                parent.searchText.error="Query can't be empty"
            else{
                createDialog()
                viewModel.getSearchMovies(query.toString())
            }
            Log.d("lol", "onActivityCreated: $query")
        }
    }

    private fun createDialog(){
        dialog =  Dialog(requireContext())
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.loading_bar)
        dialog.show()
    }
}