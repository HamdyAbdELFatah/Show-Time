package com.hamdy.showtime.ui.ui.home.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamdy.showtime.ui.ui.home.model.PopularResultsItem
import com.hamdy.showtime.ui.ui.home.repository.HomeRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {
    private val homeRepository= HomeRepository()
    var listPopular = MutableLiveData<List<PopularResultsItem>>()
    var listTopRated = MutableLiveData<List<PopularResultsItem>>()
    var listUpComing = MutableLiveData<List<PopularResultsItem>>()
    var listTrending= MutableLiveData<List<PopularResultsItem>>()

    init {
        getPopular()
    }

    private fun getPopular(){

        viewModelScope.launch(IO) {
           val list=homeRepository.getPopular()
            withContext(Main){
                listPopular.postValue(list)
            }
        }
        viewModelScope.launch(IO) {
           val list=homeRepository.getTopRated()
            withContext(Main){
                listTopRated.postValue(list)
            }
        }
        viewModelScope.launch(IO) {
           val list=homeRepository.getUpComing()
            withContext(Main){
                listUpComing.postValue(list)
            }
        }
        viewModelScope.launch(IO) {
           val list=homeRepository.getTrending()
            withContext(Main){
                listTrending.postValue(list)
            }
        }

    }

}