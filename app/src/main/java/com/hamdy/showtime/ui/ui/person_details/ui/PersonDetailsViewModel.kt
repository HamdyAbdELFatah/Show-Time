package com.hamdy.showtime.ui.ui.person_details.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamdy.showtime.ui.model.PersonDetailsResponse
import com.hamdy.showtime.ui.model.PersonsResultsItem
import com.hamdy.showtime.ui.model.ProfilesItem
import com.hamdy.showtime.ui.ui.person_details.repository.PersonDetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PersonDetailsViewModel : ViewModel() {
    private val personDetailsRepository= PersonDetailsRepository()
    var listPersons = MutableLiveData<List<ProfilesItem?>?>()
    var personDetails = MutableLiveData<PersonDetailsResponse>()


    fun getPersonDetails(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = personDetailsRepository.getPersonDetails(id)
            withContext(Dispatchers.Main) {
                personDetails.postValue(response)
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
}