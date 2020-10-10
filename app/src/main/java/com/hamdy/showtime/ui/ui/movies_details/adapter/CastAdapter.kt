package com.hamdy.showtime.ui.ui.movies_details.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.CastItemBinding
import com.hamdy.showtime.ui.model.CastItem
import com.hamdy.showtime.ui.util.ImageUrlBase


class CastAdapter : RecyclerView.Adapter<CastAdapter.Holder>() {
    private var movies: List<CastItem?>? = null
    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        context=parent.context
        return Holder(
            LayoutInflater.from(context).inflate(R.layout.cast_item, parent, false)
        )
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val movie= movies?.get(position)
        holder.castCharacter.text=movie?.character
        //holder.companyName.text=movie?.
        holder.castName.text=movie?.name
        if(movie?.profilePath!=null)
            holder.castImage.load(ImageUrlBase+ movie.profilePath){
                crossfade(true)
                crossfade(500)
                transformations(CircleCropTransformation())
            }
        else
            holder.castImage.load(R.drawable.not_found){
                crossfade(true)
                crossfade(500)
                transformations(CircleCropTransformation())
            }
    }
    override fun getItemCount(): Int {
        if(movies!=null)
            return movies!!.size
        return 0
    }

    fun setCast(movies: List<CastItem?>?) {
        this.movies = movies
        notifyDataSetChanged()
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = CastItemBinding.bind(itemView)
        val castImage = binding.castImage
        //val companyName = binding.companyName
        val castCharacter = binding.castCharacter
        val castName = binding.castName

    }
}