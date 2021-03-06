package com.hamdy.showtime.ui.ui.person_list.ui

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.PersonListFragmentBinding
import com.hamdy.showtime.ui.ui.person_list.adapter.PopularPersonAdapter

class PersonListFragment : Fragment() {

    private lateinit var viewModel: PersonListViewModel
    private lateinit var binding: PersonListFragmentBinding
    lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PersonListViewModel::class.java)
        createDialog()
        viewModel.myStart(dialog)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=PersonListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter= PopularPersonAdapter()
        binding.personListRecyclerView.adapter= adapter
        viewModel.listPersons.observe(viewLifecycleOwner, Observer {
            adapter.setPopular(it)
        })
    }
    private fun createDialog(){
        dialog =  Dialog(requireContext())
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.loading_bar)
        dialog.show()
    }
}