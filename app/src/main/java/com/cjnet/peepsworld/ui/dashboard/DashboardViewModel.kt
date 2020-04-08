package com.cjnet.peepsworld.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>()
        .apply{
        value = "Home"
    }
    val text: LiveData<String> = _text
}