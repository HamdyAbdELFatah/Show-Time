package com.hamdy.showtime.ui.ui.home.adapter

import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.hamdy.showtime.R
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
        val displayMetrics: DisplayMetrics = context?.resources?.displayMetrics!!
        val pxWidth = displayMetrics.widthPixels
        val width = pxWidth / displayMetrics.density
        val layoutParams: ViewGroup.LayoutParams = holder.trendContainer.layoutParams
        layoutParams.width = (width / 0.9).toInt()
        //layoutParams.height = (width / 0.9).toInt()
        holder.trendContainer.layoutParams = layoutParams
        holder.imageMovieTrend.load(ImageUrlBase+movie?.backdropPath)

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
        val imageMovieTrend:ImageView = itemView.findViewById(R.id.imageMovieTrend)
        val trendContainer: ConstraintLayout = itemView.findViewById(R.id.trendContainer)
    }
}