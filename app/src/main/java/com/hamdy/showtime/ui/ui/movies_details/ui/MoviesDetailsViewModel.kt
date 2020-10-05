package com.hamdy.showtime.ui.ui.movies_details.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamdy.showtime.ui.model.CastItem
import com.hamdy.showtime.ui.model.MoviesDetailsResponse
import com.hamdy.showtime.ui.ui.movies_details.repository.MoviesDetailsRepository
import kotlinx.coroutines.*

class MoviesDetailsViewModel : ViewModel() {
    private val TAG = "AllMoviesViewModel"
    private val moviesDetailsRepository= MoviesDetailsRepository()
    var listCastMovie = MutableLiveData<List<CastItem?>>()
    var movieDetails = MutableLiveData<MoviesDetailsResponse?>()


    fun getCastMovieList(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = moviesDetailsRepository.getCastMovieList(id)
            withContext(Dispatchers.Main) {
                listCastMovie.postValue(list)
            }
        }
    }
    fun getMoviesDetails(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = moviesDetailsRepository.getMoviesDetails(id)
            withContext(Dispatchers.Main) {
                movieDetails.postValue(list)
            }
        }
    }

}