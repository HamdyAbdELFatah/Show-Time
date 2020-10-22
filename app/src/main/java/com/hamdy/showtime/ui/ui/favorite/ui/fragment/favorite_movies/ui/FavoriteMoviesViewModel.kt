package com.hamdy.showtime.ui.ui.favorite.ui.fragment.favorite_movies.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamdy.showtime.ui.model.CastItem
import com.hamdy.showtime.ui.ui.favorite.ui.fragment.favorite_movies.repository.FavoriteMoviesRepository
import com.hamdy.showtime.ui.ui.movies_details.repository.MoviesDetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteMoviesViewModel : ViewModel() {
    private val moviesDetailsRepository= FavoriteMoviesRepository()
    var listFavoriteMovie = MutableLiveData<List<CastItem?>>()

    fun getFavorite(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val exist=moviesDetailsRepository.getFavorite(id)
            withContext(Dispatchers.Main){
                //todo
            }
        }
    }}