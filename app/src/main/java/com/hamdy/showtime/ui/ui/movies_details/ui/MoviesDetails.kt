package com.hamdy.showtime.ui.ui.movies_details.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import coil.load
import com.hamdy.showtime.databinding.FragmentMoviesDetailsBinding
import com.hamdy.showtime.ui.ui.movies_details.adapter.CastAdapter
import com.hamdy.showtime.ui.util.ImageUrlBase

class MoviesDetails : Fragment() {

    private lateinit var moviesDetailsViewModel: MoviesDetailsViewModel
    private lateinit var binding: FragmentMoviesDetailsBinding

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        val transition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        moviesDetailsViewModel =
            ViewModelProvider(this).get(MoviesDetailsViewModel::class.java)
        binding = FragmentMoviesDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val posterPath= arguments?.get("posterPath").toString()
        val id= arguments?.getInt("id")!!
        moviesDetailsViewModel.getCastMovieList(id)
        moviesDetailsViewModel.getMoviesDetails(id)
        binding.moviePosterImage.load(ImageUrlBase + posterPath)
        binding.moviesBackGroundImage.load(ImageUrlBase + posterPath)
        val castAdapter=CastAdapter()
        binding.castRecyclerView.adapter= castAdapter
        binding.castRecyclerView.layoutManager=LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        moviesDetailsViewModel.listCastMovie.observe(viewLifecycleOwner, Observer {
            castAdapter.setCast(it)
        })
        moviesDetailsViewModel.movieDetails.observe(viewLifecycleOwner, Observer {
            binding.movieName.text = it?.title
            binding.overviewText.text = it?.overview
            val genres = it?.genres
            var temp = ""
            for (i in genres!!) {
                temp += if (i != genres.last())
                    "${i?.name} / "
                else
                    "${i?.name}"
            }
            binding.movieCategory.text = temp
            val time = it.runtime!!
            val hours: Int = time / 60
            val minutes: Int = time % 60
            binding.movieTime.text="$hours H $minutes Min"
            binding.rateText.text=it.voteAverage.toString()
        })
    }

}