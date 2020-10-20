package com.hamdy.showtime.ui.ui.login.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.hamdy.showtime.ui.ui.login.repository.LoginRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {
    var responseLiveData= MutableLiveData<String>()
    private val loginRepository=LoginRepository()
    fun login(email:String,password:String){
        viewModelScope.launch(IO){
            val response=loginRepository.login(email,password)
            withContext(Main){
                responseLiveData.postValue(response)
            }
        }
    }

}