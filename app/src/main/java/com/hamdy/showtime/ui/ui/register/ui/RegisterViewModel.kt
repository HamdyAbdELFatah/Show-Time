package com.hamdy.showtime.ui.ui.register.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamdy.showtime.ui.ui.register.repository.RegisterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel : ViewModel() {

    var responseLiveData = MutableLiveData<String>()
    private val registerRepository = RegisterRepository()

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = registerRepository.register(name, email, password)
            withContext(Dispatchers.Main) {
                responseLiveData.postValue(response)
            }
        }
    }

}