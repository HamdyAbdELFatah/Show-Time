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
//    var totalPages = MutableLiveData<Int>()
    lateinit var dialog: Dialog

    fun myStart(type:String,dialog:Dialog){
        this.dialog=dialog
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

    private fun getPopular() {
        viewModelScope.launch(Dispatchers.Unconfined) {
            val response1 = allMoviesRepository.getPopular(1)
            list.addAll(response1?.results!!)
            for (i in 2..response1.totalPages!!) {
                val response = allMoviesRepository.getPopular(i)
                list.addAll(response?.results!!)
                if(list.size%500==0){
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

    private fun getTopRated(){
        viewModelScope.launch(Dispatchers.IO) {
            val response1 = allMoviesRepository.getTopRated(1)
            list.addAll(response1?.results!!)
            for (i in 2..response1.totalPages!!) {
                val response = allMoviesRepository.getTopRated(i)
                list.addAll(response?.results!!)
                if(list.size%200==0){
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

     private fun getUpComing() {
          viewModelScope.launch(Dispatchers.IO) {
              val response1 = allMoviesRepository.getUpComing(1)
              list.addAll(response1?.results!!)
              for (i in 2..response1.totalPages!!) {
                  val response = allMoviesRepository.getUpComing(i)
                  list.addAll(response?.results!!)
                  if (list.size % 500 == 0) {
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

}