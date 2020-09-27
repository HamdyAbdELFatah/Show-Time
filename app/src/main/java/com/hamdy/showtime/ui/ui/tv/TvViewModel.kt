package com.hamdy.showtime.ui.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TvViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is tv Fragment"
    }
    val text: LiveData<String> = _text
}