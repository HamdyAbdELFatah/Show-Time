package com.hamdy.showtime.ui.ui.all_movies.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamdy.showtime.ui.ui.all_movies.repository.AllMoviesRepository
import com.hamdy.showtime.ui.ui.home.model.PopularResultsItem
import kotlinx.coroutines.*

class AllMoviesViewModel : ViewModel() {
    private val TAG = "AllMoviesViewModel"
    private val allMoviesRepository= AllMoviesRepository()
    var listMovies = MutableLiveData<List<PopularResultsItem>>()
    var list = mutableListOf<PopularResultsItem>()
    var totalPages = MutableLiveData<Int>()
    init {
        Log.d(TAG, "Hamdy :${list.size} size ${listMovies.value?.size} ")
    }
    fun getPopular() {
        viewModelScope.launch(Dispatchers.IO) {
            for (i in 1..totalPages.value!!) {
                val response = allMoviesRepository.getPopular(i)
                withContext(Dispatchers.Main) {
                    list.addAll(response?.results!!)
                    listMovies.postValue(list)
                }
            }
        }
    }
    fun getTopRated(){
        viewModelScope.launch(Dispatchers.IO) {
            for (i in 1..totalPages.value!!) {
                val response = allMoviesRepository.getTopRated(i)
                withContext(Dispatchers.Main) {
                    list.addAll(response?.results!!)
                    listMovies.postValue(list)
                }
            }
         }
     }
     fun getUpComing() {
          viewModelScope.launch(Dispatchers.IO) {
              for (i in 1..totalPages.value!!) {
                  val response = allMoviesRepository.getUpComing(i)
                  withContext(Dispatchers.Main) {
                      list.addAll(response?.results!!)
                      listMovies.postValue(list)
                  }
              }
         }
     }
    fun getTrending() {
        viewModelScope.launch(Dispatchers.IO) {
            val response=allMoviesRepository.getTrending(1)
            withContext(Dispatchers.Main){
                list.addAll(response?.results!!)
               listMovies.postValue(list)
            }
        }
    }

    fun getPopularTotalResult() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = allMoviesRepository.getPopular(1)
            withContext(Dispatchers.Main) {
                Log.d(TAG, "getPopularTotalResult: pages ${response?.totalPages} results ${response?.totalResults}")
                totalPages.postValue(response?.totalPages)
            }
        }
    }
    fun getTopRatedTotalResult() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = allMoviesRepository.getTopRated(1)
            withContext(Dispatchers.Main) {
                totalPages.postValue(response?.totalPages)
            }
        }
    }
    fun getUpComingTotalResult() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = allMoviesRepository.getUpComing(1)
            withContext(Dispatchers.Main) {
                totalPages.postValue(response?.totalPages)
            }
        }
    }

}