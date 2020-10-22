package com.hamdy.showtime.ui.ui.favorite.ui.fragment.favorite_person.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hamdy.showtime.R

class FavoritePersonFragment : Fragment() {

    companion object {
        fun newInstance() = FavoritePersonFragment()
    }

    private lateinit var viewModel: FavoritePersonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_person_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavoritePersonViewModel::class.java)
        // TODO: Use the ViewModel
    }

}