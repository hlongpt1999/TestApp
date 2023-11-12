package com.example.testandroid.module.dashboard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashBoardViewModel: ViewModel() {

    private val _data = MutableLiveData<String>("")
    val data: LiveData<String> = _data

    fun setData(sharedData: String) {
        _data.value = sharedData
    }

}