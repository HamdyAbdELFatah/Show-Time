package com.hamdy.showtime.ui.ui.person_list.ui

import android.app.Application
import android.app.Dialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamdy.showtime.R
import com.hamdy.showtime.ui.model.PersonsResponse
import com.hamdy.showtime.ui.model.PersonsResultsItem
import com.hamdy.showtime.ui.model.PopularResultsItem
import com.hamdy.showtime.ui.ui.person_list.repository.PersonsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PersonListViewModel : ViewModel() {
    private val personsRepository= PersonsRepository()
    var listPersons = MutableLiveData<List<PersonsResultsItem>>()
    var list = mutableListOf<PersonsResultsItem>()
    lateinit var dialog:Dialog

    fun myStart(dialog:Dialog){
        this.dialog=dialog
        getPopular()
    }

    private fun getPopular() {
        viewModelScope.launch(Dispatchers.Unconfined) {
            val response1 = personsRepository.getPopular(1)
            list.addAll(response1?.personsResults!!)
            for (i in 2..response1.totalPages!!) {
                val response = personsRepository.getPopular(i)
                list.addAll(response?.personsResults!!)
                if(list.size%500==0){
                    withContext(Dispatchers.Main) {
                        listPersons.postValue(list)
                        dialog.cancel()
                    }
                }
            }
            withContext(Dispatchers.Main) {
                listPersons.postValue(list)
            }
        }
    }

}