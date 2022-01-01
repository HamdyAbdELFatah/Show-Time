package com.hamdy.showtime.ui.ui.search.fragment.person_search.ui

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
import com.hamdy.showtime.databinding.SearchPersonFragmentBinding
import com.hamdy.showtime.ui.model.ResultsItem
import com.hamdy.showtime.ui.ui.search.adapter.SearchMoviesAdapter
import com.hamdy.showtime.ui.ui.search.adapter.SearchPersonAdapter
import com.hamdy.showtime.ui.ui.search.ui.SearchFragment
import com.hamdy.showtime.ui.ui.search.ui.SharedViewModel
import com.hamdy.showtime.ui.util.CenterZoomLayoutManager
import com.hamdy.showtime.ui.util.ImageUrlBase
import kotlinx.android.synthetic.main.search_fragment.*

class SearchPersonFragment : Fragment() {


    private lateinit var viewModel: SearchPersonViewModel
    private lateinit var sharedViewModel: SharedViewModel

    private lateinit var binding: SearchPersonFragmentBinding
    private lateinit var snapHelper: LinearSnapHelper
    private lateinit var arr: List<ResultsItem?>
    private var position = 0
    lateinit var dialog: Dialog
    private var firstOpen = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchPersonFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SearchPersonViewModel::class.java]
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        val layoutManger = CenterZoomLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.searchPersonsRecyclerView.layoutManager = layoutManger
        snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.searchPersonsRecyclerView)
        val searchAdapter = SearchPersonAdapter()
        binding.searchPersonsRecyclerView.adapter = searchAdapter
        viewModel.listSearchPerson.observe(viewLifecycleOwner, {
            arr = it
            if (arr.isNotEmpty())
                setBackgroundImage(position)
            if(dialog.isShowing)
                dialog.cancel()
            searchAdapter.setQuery(it, R.id.action_searchFragment_to_navigation_person)
        })
        binding.searchPersonsRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val centerView = snapHelper.findSnapView(layoutManger)
                    val pos: Int = layoutManger.getPosition(centerView!!)
                    setBackgroundImage(pos)
                }
            }
        })

        sharedViewModel.searchQuery.observe(viewLifecycleOwner) {
            val parent = this.parentFragment
            if (it.second == 1 && firstOpen) {
                val query = it.first
                if (query.isEmpty())
                    parent?.searchText?.error = "Query can't be empty"
                else {
                    createDialog()
                    Log.d("lol", "aha: $query")

                    viewModel.getSearchPersons(query)
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
        binding.backGroundFavorite.load(ImageUrlBase + arr[position]?.profilePath) {
            crossfade(true)
            crossfade(1000)
            transformations(BlurTransformation(requireContext(), 25f))
        }
    }
}