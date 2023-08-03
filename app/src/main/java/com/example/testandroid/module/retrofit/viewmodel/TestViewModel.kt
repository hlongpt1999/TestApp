package com.example.testandroid.module.retrofit.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.testandroid.api.APIClient
import com.example.testandroid.base.BaseViewModel
import com.example.testandroid.function.Messages
import com.example.testandroid.module.retrofit.api.ITestService
import com.example.testandroid.module.retrofit.model.TestModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TestViewModel: BaseViewModel() {

    val testLiveData = MutableLiveData<TestModel>()

    fun getTestApi() {
        val quotesAPI = APIClient.getInstance().create(ITestService::class.java)
        GlobalScope.launch {
            val result = quotesAPI.getTestData()
            if (result.isSuccessful) {
                result.body()?.let {
                    testLiveData.postValue(it)
                }
            } else {
                message.postValue(Messages.getMessageFromStatusCode())
            }
        }
    }
}