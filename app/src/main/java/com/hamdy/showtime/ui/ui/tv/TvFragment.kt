package com.hamdy.showtime.ui.ui.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hamdy.showtime.R
import com.hamdy.showtime.ui.ui.favorite.FavoriteViewModel

class TvFragment : Fragment() {

    private lateinit var tvViewModel: TvViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tvViewModel =
            ViewModelProvider(this).get(TvViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tv, container, false)
        val textView: TextView = root.findViewById(R.id.text_tv)
        tvViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}