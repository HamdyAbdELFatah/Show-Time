package com.hamdy.showtime.ui.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.MoviesItemBinding
import com.hamdy.showtime.ui.ui.home.model.PopularResultsItem
import com.hamdy.showtime.ui.util.ImageUrlBase


class UpComingAdapter : RecyclerView.Adapter<UpComingAdapter.Holder>() {
    private var movies: List<PopularResultsItem>? = null
    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        context=parent.context
        return Holder(
            LayoutInflater.from(context).inflate(R.layout.movies_item, parent, false)
        )
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val movie= movies?.get(position)
        holder.moviesName.text=movie?.originalTitle
        //holder.companyName.text=movie?.
        holder.rateText.text=movie?.voteAverage.toString()
        holder.movieImage.load(ImageUrlBase+movie?.posterPath)
    }
    override fun getItemCount(): Int {
        if(movies!=null)
            return movies!!.size
        return 0
    }

    fun setUpComing(movies: List<PopularResultsItem>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = MoviesItemBinding.bind(itemView)
        val movieImage = binding.movieImage
        //val companyName = binding.companyName
        val moviesName = binding.moviesName
        val rateText = binding.rateText

    }
}