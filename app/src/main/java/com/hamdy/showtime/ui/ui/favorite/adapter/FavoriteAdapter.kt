package com.hamdy.showtime.ui.ui.favorite.adapter

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.FavoriteItemBinding
import com.hamdy.showtime.ui.ui.favorite.model.FavoriteItem
import com.hamdy.showtime.ui.util.ImageUrlBase

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.Holder>() {
    private var favorites: List<FavoriteItem>? = null
    private var action: Int? = null
    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        context = parent.context
        return Holder(
            LayoutInflater.from(context).inflate(R.layout.favorite_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val favorite = favorites?.get(position)
        //holder.companyName.text=movie?.
        holder.movieImage.load(ImageUrlBase + favorite?.poster) {
            crossfade(true)
            crossfade(500)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.movieImage.clipToOutline = true
        }
//        ViewCompat.setTransitionName(holder.movieImage, "${type}Image$position")
//        ViewCompat.setTransitionName(holder.moviesName, "${type}Text$position")

        holder.movieImage.setOnClickListener {
//            val extras = FragmentNavigator.Extras.Builder()
//                .addSharedElement(holder.movieImage, ViewCompat.getTransitionName(holder.movieImage)!!)
//                .addSharedElement(holder.moviesName, ViewCompat.getTransitionName(holder.moviesName)!!)
//                .build()
            val bundle = Bundle()
            bundle.putString("posterPath", favorite?.poster!!)
            bundle.putInt("id", favorite.favoriteId.toInt())
//            bundle.putInt("position", position)
//            bundle.putString("type", type)
            it.findNavController().navigate(action!!, bundle, null, null)
        }

    }

    override fun getItemCount(): Int {
        if (favorites != null)
            return favorites!!.size
        return 0
    }


    fun setFavorite(favorites: List<FavoriteItem>) {
        this.favorites = favorites
        notifyDataSetChanged()
    }

    fun setFavorite(favorites: List<FavoriteItem>, action: Int) {
        this.favorites = favorites
        this.action = action
        notifyDataSetChanged()
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = FavoriteItemBinding.bind(itemView)
        val movieImage = binding.imageMovieFavorite
        //val companyName = binding.companyName
    }
}