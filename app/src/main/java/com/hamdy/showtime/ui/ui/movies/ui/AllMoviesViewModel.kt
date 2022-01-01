package com.hamdy.showtime.ui.ui.movies.ui

import android.app.Dialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamdy.showtime.ui.ui.movies.repository.AllMoviesRepository
import com.hamdy.showtime.ui.model.PopularResultsItem
import kotlinx.coroutines.*

class AllMoviesViewModel : ViewModel() {
    private val TAG = "AllMoviesViewModel"
    private val allMoviesRepository = AllMoviesRepository()
    var listMovies = MutableLiveData<List<PopularResultsItem>>()
    var list = mutableListOf<PopularResultsItem>()

    //    var totalPages = MutableLiveData<Int>()
    lateinit var dialog: Dialog
    private var page = 1

    fun myStart(type: String, dialog: Dialog) {
        this.dialog = dialog
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
            val response1 = allMoviesRepository.getPopular(page)
            list.addAll(response1?.results!!)

            withContext(Dispatchers.Main) {
                listMovies.postValue(list)
                if(dialog.isShowing)
                    dialog.cancel()
                page++
            }

        }
    }
    fun getTopRated() {
        viewModelScope.launch(Dispatchers.Unconfined) {
            val response1 = allMoviesRepository.getTopRated(page)
            list.addAll(response1?.results!!)

            withContext(Dispatchers.Main) {
                listMovies.postValue(list)
                if(dialog.isShowing)
                    dialog.cancel()
                page++
            }

        }
    }
    fun getUpComing() {
        viewModelScope.launch(Dispatchers.Unconfined) {
            val response1 = allMoviesRepository.getUpComing(page)
            list.addAll(response1?.results!!)
            withContext(Dispatchers.Main) {
                listMovies.postValue(list)
                if(dialog.isShowing)
                    dialog.cancel()
                page++
            }

        }
    }

}