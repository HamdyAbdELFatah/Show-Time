package com.hamdy.showtime.ui.ui.person_list.adapter

import android.content.Context
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
import com.hamdy.showtime.databinding.PersonsItemBinding
import com.hamdy.showtime.ui.model.PersonsResultsItem
import com.hamdy.showtime.ui.util.ImageUrlBase
import kotlin.random.Random


class PopularPersonAdapter : RecyclerView.Adapter<PopularPersonAdapter.Holder>() {
    private var movies: List<PersonsResultsItem>? = null
    private var personsList: MutableMap<String, Boolean>? = null
    private var action: Int? = null
    var context: Context? = null
    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        context = parent.context
        return Holder(
            LayoutInflater.from(context).inflate(R.layout.persons_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val person = movies?.get(position)
        holder.personName.text = person?.name
        holder.personRate.text = person?.popularity.toString()
        //holder.companyName.text=movie?.
        holder.movieImage.load(ImageUrlBase + person?.profilePath) {
            crossfade(true)
            crossfade(500)
        }
//        ViewCompat.setTransitionName(holder.movieImage, "${type}Image$position")
//        ViewCompat.setTransitionName(holder.moviesName, "${type}Text$position")
        holder.favoriteImage.setImageResource(
            if (personsList?.get(person?.id.toString()) == true)
                R.drawable.ic_favorite_choosed else R.drawable.ic_favorite
        )
        setAnimation(holder.itemView, position)
        holder.movieContainer.setOnClickListener {
//            val extras = FragmentNavigator.Extras.Builder()
//                .addSharedElement(holder.movieImage, ViewCompat.getTransitionName(holder.movieImage)!!)
//                .addSharedElement(holder.moviesName, ViewCompat.getTransitionName(holder.moviesName)!!)
//                .build()
            val bundle = Bundle()
            bundle.putString("posterPath", person?.profilePath!!)
            bundle.putInt("id", person.id!!)
//            bundle.putInt("position", position)
//            bundle.putString("type", type)
            it.findNavController()
                .navigate(R.id.action_personListFragment_to_navigation_person, bundle, null, null)

        }

    }

    override fun getItemCount(): Int {
        if (movies != null)
            return movies!!.size
        return 0
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val anim = ScaleAnimation(
                0.0f,
                1.0f,
                0.0f,
                1.0f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )
            anim.duration = Random.nextLong(501) //to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim)
            lastPosition = position
        }
    }

    fun setPopular(movies: List<PersonsResultsItem>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    fun setFavorite(personsList: MutableMap<String, Boolean>) {
        this.personsList = personsList
    }

    fun updateFavorite(person: Pair<String, Boolean>) {
        if (personsList != null) {
            personsList!![person.first] = person.second
            notifyDataSetChanged()
        }
    }
    /*fun setPopular(movies: List<PopularResultsItem>,action:Int,type:String) {
        this.movies = movies
        this.action = action
        this.type = type
        notifyDataSetChanged()
    }*/

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = PersonsItemBinding.bind(itemView)
        val movieContainer = binding.personContainer
        val movieImage = binding.personImage
        val favoriteImage = binding.favoriteImage

        //val companyName = binding.companyName
        val personName = binding.personName
        val personRate = binding.personRate
    }
}