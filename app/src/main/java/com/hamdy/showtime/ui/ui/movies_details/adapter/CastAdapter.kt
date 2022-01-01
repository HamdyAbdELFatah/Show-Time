package com.hamdy.showtime.ui.ui.movies_details.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.CastItemBinding
import com.hamdy.showtime.ui.model.CastItem
import com.hamdy.showtime.ui.util.ImageUrlBase


class CastAdapter : RecyclerView.Adapter<CastAdapter.Holder>() {
    private var casts: List<CastItem?>? = null
    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        context = parent.context
        return Holder(
            LayoutInflater.from(context).inflate(R.layout.cast_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val cast = casts?.get(position)
        holder.castCharacter.text = cast?.character
        //holder.companyName.text=movie?.
        holder.castName.text = cast?.name
        if (cast?.profilePath != null)
            holder.castImage.load(ImageUrlBase + cast.profilePath) {
                crossfade(true)
                crossfade(500)
                transformations(CircleCropTransformation())
            }
        else
            holder.castImage.load(R.drawable.not_found) {
                crossfade(true)
                crossfade(500)
                transformations(CircleCropTransformation())
            }
        holder.castContainer.setOnClickListener {
            val bundle = bundleOf("id" to cast?.id)
            bundle.putString("posterPath", cast?.profilePath)
            it.findNavController()
                .navigate(R.id.action_moviesDetails_to_navigation_person, bundle, null, null)

        }

    }

    override fun getItemCount(): Int {
        if (casts != null)
            return casts!!.size
        return 0
    }

    fun setCast(movies: List<CastItem?>?) {
        this.casts = movies
        notifyDataSetChanged()
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = CastItemBinding.bind(itemView)
        val castContainer = binding.castContainer
        val castImage = binding.castImage

        //val companyName = binding.companyName
        val castCharacter = binding.castCharacter
        val castName = binding.castName

    }
}