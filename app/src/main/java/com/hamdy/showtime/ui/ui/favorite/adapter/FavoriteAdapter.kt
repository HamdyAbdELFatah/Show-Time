package com.hamdy.showtime.ui.ui.favorite.adapter

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.FavoriteItemBinding
import com.hamdy.showtime.databinding.PersonsItemBinding
import com.hamdy.showtime.ui.model.PersonsResultsItem
import com.hamdy.showtime.ui.ui.person_list.adapter.PopularPersonAdapter
import com.hamdy.showtime.ui.util.ImageUrlBase
import kotlin.random.Random

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.Holder>() {
    private var movies: List<PersonsResultsItem>? = null
    private var action: Int? = null
    var context: Context? = null
    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        context=parent.context
        return Holder(
            LayoutInflater.from(context).inflate(R.layout.favorite_item, parent, false)
        )
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val person= movies?.get(position)
        //holder.companyName.text=movie?.
        val arr= listOf(R.drawable.test,R.drawable.test2,R.drawable.test3,R.drawable.test4,R.drawable.test,R.drawable.test2)
        holder.movieImage.load(arr[position]){
            crossfade(true)
            crossfade(500)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.movieImage.clipToOutline = true
        }
//        ViewCompat.setTransitionName(holder.movieImage, "${type}Image$position")
//        ViewCompat.setTransitionName(holder.moviesName, "${type}Text$position")

//        holder.movieContainer.setOnClickListener {
////            val extras = FragmentNavigator.Extras.Builder()
////                .addSharedElement(holder.movieImage, ViewCompat.getTransitionName(holder.movieImage)!!)
////                .addSharedElement(holder.moviesName, ViewCompat.getTransitionName(holder.moviesName)!!)
////                .build()
//            val bundle = Bundle()
//            bundle.putString("posterPath", person?.profilePath!!)
//            bundle.putInt("id", person.id!!)
////            bundle.putInt("position", position)
////            bundle.putString("type", type)
//            it.findNavController().navigate(R.id.action_personListFragment_to_navigation_person,bundle,null,null)
//        }

    }
    override fun getItemCount(): Int {
        if(movies!=null)
            return movies!!.size
        return 5
    }



    fun setPopular(movies: List<PersonsResultsItem>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = FavoriteItemBinding.bind(itemView)
        val movieImage = binding.imageMovieFavorite
        //val companyName = binding.companyName
    }
}