package com.hamdy.showtime.ui.ui.favorite.ui.fragment.favorite_person.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamdy.showtime.ui.ui.favorite.model.FavoriteItem
import com.hamdy.showtime.ui.ui.favorite.ui.fragment.favorite_person.repository.FavoritePersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritePersonViewModel : ViewModel() {
    private val favoritePersonRepository= FavoritePersonRepository()
    var listFavoriteMovie = MutableLiveData<List<FavoriteItem>>()

    fun getFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            val favorite=favoritePersonRepository.getFavorite()
            withContext(Dispatchers.Main){
                listFavoriteMovie.postValue(favorite)
            }
        }
    }}