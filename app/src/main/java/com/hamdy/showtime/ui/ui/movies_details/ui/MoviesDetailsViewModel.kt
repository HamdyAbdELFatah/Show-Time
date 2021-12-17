package com.hamdy.showtime.ui.ui.movies_details.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamdy.showtime.ui.model.CastItem
import com.hamdy.showtime.ui.model.MoviesDetailsResponse
import com.hamdy.showtime.ui.ui.movies_details.repository.MoviesDetailsRepository
import com.hamdy.showtime.ui.ui.person_details.repository.PersonDetailsRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class MoviesDetailsViewModel : ViewModel() {
    private val TAG = "AllMoviesViewModel"
    private val moviesDetailsRepository= MoviesDetailsRepository()
    var listCastMovie = MutableLiveData<List<CastItem?>>()
    var favorite = MutableLiveData<Boolean>()
    var movieDetails = MutableLiveData<MoviesDetailsResponse?>()


    fun getCastMovieList(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = moviesDetailsRepository.getCastMovieList(id)
            withContext(Dispatchers.Main) {
                listCastMovie.postValue(list!!)
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

    fun getFavorite(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val exist=moviesDetailsRepository.getFavorite(id)
            withContext(Main){
                favorite.postValue(exist)
            }
        }
    }
    fun setFavorite(id:Int,poster:String,exist:Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            moviesDetailsRepository.setFavorite(id,poster,exist)
        }
    }

}