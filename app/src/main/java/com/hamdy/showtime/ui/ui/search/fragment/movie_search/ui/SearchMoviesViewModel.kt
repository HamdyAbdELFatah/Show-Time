package com.hamdy.showtime.ui.ui.search.fragment.movie_search.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamdy.showtime.ui.model.SearchResultsItem
import com.hamdy.showtime.ui.ui.search.fragment.movie_search.repository.SearchMoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchMoviesViewModel : ViewModel() {
    private val moviesDetailsRepository= SearchMoviesRepository()
    var listSearchMovie = MutableLiveData<List<SearchResultsItem?>>()

    fun getSearchMovies(query:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response=moviesDetailsRepository.getSearchMovies(query)
            withContext(Dispatchers.Main){
                listSearchMovie.postValue(response.results!!)
            }
        }
    }
}