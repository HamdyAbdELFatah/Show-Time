package com.hamdy.showtime.ui.ui.person_list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hamdy.showtime.R

class PersonListFragment : Fragment() {

    companion object {
        fun newInstance() = PersonListFragment()
    }

    private lateinit var viewModel: PersonListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.person_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PersonListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}