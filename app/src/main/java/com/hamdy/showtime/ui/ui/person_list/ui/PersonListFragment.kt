package com.hamdy.showtime.ui.ui.person_list.ui

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.PersonListFragmentBinding
import com.hamdy.showtime.ui.ui.person_list.adapter.PopularPersonAdapter
import com.hamdy.showtime.ui.util.MyItemDecoration


class PersonListFragment : Fragment() {

    private lateinit var viewModel: PersonListViewModel
    private lateinit var binding: PersonListFragmentBinding
    private var canGetPopular = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[PersonListViewModel::class.java]
        viewModel.myStart(createDialog())

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PersonListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PopularPersonAdapter()
        binding.personListRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, VERTICAL)
        binding.personListRecyclerView.addItemDecoration(MyItemDecoration(requireContext()))
        binding.personListRecyclerView.adapter = adapter

        viewModel.listPersons.observe(viewLifecycleOwner) {
            adapter.setPopular(it)
            binding.loadingMoreProgressBar.visibility = View.GONE
            canGetPopular = true
        }
        viewModel.listFavoritePersons.observe(viewLifecycleOwner) {
            adapter.setFavorite(it)
            canGetPopular = true
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Pair<String, Boolean>>(
            "favoriteBack"
        )?.observe(
            viewLifecycleOwner
        ) {
            adapter.updateFavorite(it)
        }
        binding.personListRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) { //1 for down
                    if (canGetPopular) {
                        viewModel.getPopular()
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
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.loading_bar)
        dialog.show()
        return dialog
    }
}