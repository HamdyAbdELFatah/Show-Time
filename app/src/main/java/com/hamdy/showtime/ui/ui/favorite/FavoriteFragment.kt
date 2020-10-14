package com.hamdy.showtime.ui.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hamdy.showtime.R
import com.hamdy.showtime.ui.ui.movies.ui.AllMoviesViewModel

class FavoriteFragment : Fragment() {

    private lateinit var favoriteViewModel: AllMoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favoriteViewModel =
            ViewModelProvider(this).get(AllMoviesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_favorite, container, false)
        val textView: TextView = root.findViewById(R.id.text_favorite)
//        favoriteViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        
        return root
    }
}