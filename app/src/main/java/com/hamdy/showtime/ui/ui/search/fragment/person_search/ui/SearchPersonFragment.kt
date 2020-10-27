package com.hamdy.showtime.ui.ui.search.fragment.person_search.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.SearchMoviesFragmentBinding
import com.hamdy.showtime.databinding.SearchPersonFragmentBinding

class SearchPersonFragment : Fragment() {


    private lateinit var viewModel: SearchPersonViewModel
    private lateinit var binding: SearchPersonFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = SearchPersonFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchPersonViewModel::class.java)

    }
}