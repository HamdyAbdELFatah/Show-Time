package com.hamdy.showtime.ui.ui.person_details.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.PersonsGalleryBinding
import com.hamdy.showtime.ui.model.ProfilesItem
import com.hamdy.showtime.ui.util.ImageUrlBase
import kotlin.random.Random


class PersonPhotosAdapter : RecyclerView.Adapter<PersonPhotosAdapter.Holder>() {
    private var person: List<ProfilesItem?>? = null
    private var action: Int? = null
    var context: Context? = null
    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        context=parent.context
        return Holder(
            LayoutInflater.from(context).inflate(R.layout.persons_gallery, parent, false)
        )
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val person= person?.get(position)
        holder.movieImage.load(ImageUrlBase + person?.filePath){
            crossfade(true)
            crossfade(500)
        }
        setAnimation(holder.itemView, position)
    }
    override fun getItemCount(): Int {
        if(person!=null)
            return person!!.size
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

    fun setPerson(person: List<ProfilesItem?>?) {
        this.person = person
        notifyDataSetChanged()
    }
    /*fun setPopular(movies: List<PopularResultsItem>,action:Int,type:String) {
        this.movies = movies
        this.action = action
        this.type = type
        notifyDataSetChanged()
    }*/

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = PersonsGalleryBinding.bind(itemView)
        val movieImage = binding.personImage
    }
}