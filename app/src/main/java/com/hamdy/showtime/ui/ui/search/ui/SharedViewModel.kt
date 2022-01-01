package com.hamdy.showtime.ui.ui.search.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    var searchQuery = MutableLiveData<Pair<String, Int>>()

    fun getSearchMovies(query: String, position: Int) {
        searchQuery.value = query to position
    }
}