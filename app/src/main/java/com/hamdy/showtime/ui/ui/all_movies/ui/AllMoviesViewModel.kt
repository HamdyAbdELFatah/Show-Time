package com.hamdy.showtime.ui.ui.all_movies.ui

import android.app.Dialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamdy.showtime.R
import com.hamdy.showtime.ui.ui.all_movies.repository.AllMoviesRepository
import com.hamdy.showtime.ui.model.PopularResultsItem
import kotlinx.coroutines.*

class AllMoviesViewModel : ViewModel() {
    private val TAG = "AllMoviesViewModel"
    private val allMoviesRepository= AllMoviesRepository()
    var listMovies = MutableLiveData<List<PopularResultsItem>>()
    var list = mutableListOf<PopularResultsItem>()
    var totalPages = MutableLiveData<Int>()
    lateinit var dialog: Dialog
    fun myStart(type:String,context:Context){
        dialog =  Dialog(context)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(R.layout.loading_bar)
        dialog.show()
        when (type) {
            "popular" -> {
                getPopular()
            }
            "topRate" -> {
                getTopRated()
            }
            else -> {
                getUpComing()
            }
        }
    }
    fun getPopular() {
        viewModelScope.launch(Dispatchers.Unconfined) {
            for (i in 1..500) {
                val response = allMoviesRepository.getPopular(i)
                list.addAll(response?.results!!)
                Log.d(TAG, "getPopular: ${list.size}")
                if(list.size%1000==0){
                    withContext(Dispatchers.Main) {
                        listMovies.postValue(list)
                        dialog.cancel()
                    }
                }
            }
            withContext(Dispatchers.Main) {
                listMovies.postValue(list)
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
//    fun getTrending() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val response=allMoviesRepository.getTrending(1)
//            withContext(Dispatchers.Main){
//                list.addAll(response?.results!!)
//               listMovies.postValue(list)
//            }
//        }
//    }
//
//    fun getPopularTotalResult() {
//        val job=viewModelScope.launch(Dispatchers.IO) {
//            val response = allMoviesRepository.getPopular(1)
//            withContext(Dispatchers.Main) {
//                Log.d(TAG, "getPopularTotalResult: pages ${response?.totalPages} results ${response?.totalResults}")
//                totalPages.postValue(response?.totalPages)
//            }
//        }
//        if(job.isCompleted)
//            Log.d(TAG, "getPopularTotalResult: Completd $totalPages")
//    }
//    fun getTopRatedTotalResult() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val response = allMoviesRepository.getTopRated(1)
//            withContext(Dispatchers.Main) {
//                Log.d(TAG, "getTopRatedTotalResult: pages ${response?.totalPages} results ${response?.totalResults}")
//                totalPages.postValue(response?.totalPages)
//            }
//        }
//    }
//    fun getUpComingTotalResult() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val response = allMoviesRepository.getUpComing(1)
//            withContext(Dispatchers.Main) {
//                Log.d(TAG, "getUpComingTotalResult: pages ${response?.totalPages} results ${response?.totalResults}")
//                totalPages.postValue(response?.totalPages)
//            }
//        }
//    }

}