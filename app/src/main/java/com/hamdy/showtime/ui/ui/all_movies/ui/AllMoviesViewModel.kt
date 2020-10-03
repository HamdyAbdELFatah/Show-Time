package com.hamdy.showtime.ui.ui.all_movies.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamdy.showtime.ui.ui.all_movies.repository.AllMoviesRepository
import com.hamdy.showtime.ui.ui.home.model.PopularResultsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllMoviesViewModel : ViewModel() {

    private val allMoviesRepository= AllMoviesRepository()
    var listMovies = MutableLiveData<List<PopularResultsItem>>()
     var list = mutableListOf<PopularResultsItem>()
    var totalPages = MutableLiveData<Int>()
    fun getPopular(page:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = allMoviesRepository.getPopular(page)
            withContext(Dispatchers.Main) {
                list.addAll(response?.results!!)
                listMovies.postValue(list)
            }
        }
    }
    fun getTopRated(page:Int) {
         viewModelScope.launch(Dispatchers.IO) {
             val response = allMoviesRepository.getTopRated(page)
             withContext(Dispatchers.Main) {
                 list.addAll(response?.results!!)
                 listMovies.postValue(list)
             }
         }
     }
     fun getUpComing(page:Int) {
         viewModelScope.launch(Dispatchers.IO) {
             val response = allMoviesRepository.getUpComing(page)
             withContext(Dispatchers.Main) {
                 list.addAll(response?.results!!)
                 listMovies.postValue(list)
             }
         }
     }
    fun getTrending(page:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response=allMoviesRepository.getTrending(page)
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

    override fun onCleared() {
        super.onCleared()
        list.clear()
    }
}