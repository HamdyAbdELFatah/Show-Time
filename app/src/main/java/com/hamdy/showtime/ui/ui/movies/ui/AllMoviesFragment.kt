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
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.FragmentAllMoviesBinding
import com.hamdy.showtime.ui.ui.home.adapter.HomeCategoryMoviesAdapter

class AllMoviesFragment : Fragment() {
    private val TAG = "AllMoviesFragment"
    private lateinit var allMoviesViewModel: AllMoviesViewModel
    private lateinit var binding: FragmentAllMoviesBinding
    private lateinit var type: String
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
            ViewModelProvider(this).get(AllMoviesViewModel::class.java)
        type = arguments?.get("type").toString()
        allMoviesViewModel.myStart(type, createDialog())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onActivityCreated: Called")

        binding.allMoviesRecyclerView.layoutManager = GridLayoutManager(context, 2)

        val adapter = HomeCategoryMoviesAdapter()

        binding.allMoviesRecyclerView.adapter = adapter

        allMoviesViewModel.listMovies.observe(viewLifecycleOwner, Observer {
            adapter.setMoviesData(it, R.id.action_fragment_all_movies_to_moviesDetails)
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