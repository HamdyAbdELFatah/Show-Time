package com.hamdy.showtime.ui.ui.favorite.ui.fragment.favorite_person.ui

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
import coil.transform.BlurTransformation
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.FavoritePersonFragmentBinding
import com.hamdy.showtime.ui.ui.favorite.adapter.FavoriteAdapter
import com.hamdy.showtime.ui.ui.favorite.model.FavoriteItem
import com.hamdy.showtime.ui.util.CenterZoomLayoutManager
import com.hamdy.showtime.ui.util.ImageUrlBase

class FavoritePersonFragment : Fragment() {

    private lateinit var binding: FavoritePersonFragmentBinding
    private lateinit var snapHelper: LinearSnapHelper
    private lateinit var arr: List<FavoriteItem>
    private lateinit var viewModel: FavoritePersonViewModel
    lateinit var dialog: Dialog
    private var position =0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding =FavoritePersonFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavoritePersonViewModel::class.java)
        createDialog()
        viewModel.getFavorite()
        val layoutManger= CenterZoomLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.favoriteRecyclerView.layoutManager=layoutManger
        snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.favoriteRecyclerView)
        val favoriteAdapter= FavoriteAdapter()
        binding.favoriteRecyclerView.adapter = favoriteAdapter
        viewModel.listFavoriteMovie.observe(viewLifecycleOwner, {
            arr=it
            if(arr.isNotEmpty())
                setBackgroundImage(position)
            dialog.cancel()
            favoriteAdapter.setFavorite(it, R.id.action_navigation_favorite_to_navigation_person)
        })
        binding.favoriteRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
    }
    private fun createDialog(){
        dialog =  Dialog(requireContext())
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.loading_bar)
        dialog.show()
    }
    private fun setBackgroundImage(position: Int) {
        binding.backGroundFavorite.load(ImageUrlBase + arr[position].poster) {
            crossfade(true)
            crossfade(1000)
            transformations(BlurTransformation(requireContext(), 25f))
        }
    }
}