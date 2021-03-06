package com.hamdy.showtime.ui.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.ItemTrendingBinding
import com.hamdy.showtime.ui.model.PopularResultsItem
import com.hamdy.showtime.ui.util.ImageUrlBase


class TrendingAdapter : RecyclerView.Adapter<TrendingAdapter.Holder>() {
    private var movies: List<PopularResultsItem>? = null
    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        context=parent.context
        return Holder(
            LayoutInflater.from(context).inflate(R.layout.item_trending, parent, false)
        )
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val movie= movies?.get(position)
        holder.imageMovieTrend.load(ImageUrlBase+movie?.posterPath){
            transformations(CircleCropTransformation())
            crossfade(true)
            crossfade(1000)
        }
            holder.movieTitle.text = movie?.title
    }
    override fun getItemCount(): Int {
        if(movies!=null)
            return movies!!.size
        return 0
    }

    fun setTrends(movies: List<PopularResultsItem>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding=ItemTrendingBinding.bind(itemView)
        val imageMovieTrend = binding.imageMovieTrend
        val movieTitle = binding.movieTitle
    }
}