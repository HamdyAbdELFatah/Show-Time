package com.hamdy.showtime.ui.ui.person_details.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamdy.showtime.ui.model.SearchKnownForItem
import com.hamdy.showtime.ui.model.PersonDetailsResponse
import com.hamdy.showtime.ui.model.ProfilesItem
import com.hamdy.showtime.ui.ui.person_details.repository.PersonDetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PersonDetailsViewModel : ViewModel() {
    private val personDetailsRepository= PersonDetailsRepository()
    var listPersons = MutableLiveData<List<ProfilesItem?>?>()
    var listKnown = MutableLiveData<List<SearchKnownForItem?>?>()
    var favorite = MutableLiveData<Boolean>()
    var personDetails = MutableLiveData<PersonDetailsResponse>()


    fun getPersonDetails(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = personDetailsRepository.getPersonDetails(id)
            val response1 = personDetailsRepository.getPersonKnownMovies(response.imdbId)
            withContext(Dispatchers.Main) {
                personDetails.postValue(response)
                listKnown.postValue(response1)
            }
        }
    }

    fun getPersonImage(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = personDetailsRepository.getPersonImage(id)
            withContext(Dispatchers.Main) {
                listPersons.postValue(response)
            }
        }
    }

    fun getFavorite(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val exist=personDetailsRepository.getFavorite(id)
            withContext(Dispatchers.Main){
                favorite.postValue(exist)
            }
        }
    }
    fun setFavorite(id:Int,poster:String,exist:Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            personDetailsRepository.setFavorite(id,poster,exist)
        }
    }
}