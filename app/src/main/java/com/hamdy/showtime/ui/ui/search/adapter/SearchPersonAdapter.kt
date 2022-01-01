package com.hamdy.showtime.ui.ui.search.adapter

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.FavoriteItemBinding
import com.hamdy.showtime.ui.model.ResultsItem
import com.hamdy.showtime.ui.model.SearchResultsItem
import com.hamdy.showtime.ui.util.ImageUrlBase

class SearchPersonAdapter : RecyclerView.Adapter<SearchPersonAdapter.Holder>() {
    private var queries: List<ResultsItem?>? = null
    private var action: Int? = null
    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        context=parent.context
        return Holder(
            LayoutInflater.from(context).inflate(R.layout.favorite_item, parent, false)
        )
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val query= queries?.get(position)
        //holder.companyName.text=movie?.
        if(query?.profilePath ==null)
            holder.movieImage.load(R.drawable.not_found){
                scale(Scale.FILL)
            }
        else
            holder.movieImage.load(ImageUrlBase+query.profilePath){
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
            bundle.putString("posterPath", query?.profilePath.toString())
            bundle.putInt("id", query?.id!!)
//            bundle.putInt("position", position)
//            bundle.putString("type", type)
            it.findNavController().navigate(action!!,bundle,null,null)
        }

    }
    override fun getItemCount(): Int {
        if(queries!=null)
            return queries!!.size
        return 0
    }



    fun setQuery(favorites: List<ResultsItem>) {
        this.queries = favorites
        notifyDataSetChanged()
    }
    fun setQuery(favorites: List<ResultsItem?>, action:Int) {
        this.queries = favorites
        this.action = action
        notifyDataSetChanged()
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = FavoriteItemBinding.bind(itemView)
        val movieImage = binding.imageMovieFavorite
        //val companyName = binding.companyName
    }
}