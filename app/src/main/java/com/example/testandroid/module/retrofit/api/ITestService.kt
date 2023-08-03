package com.example.testandroid.module.retrofit.api

import com.example.testandroid.module.retrofit.model.TestModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ITestService {

    @GET("todos/1")
    suspend fun getTestData(): Response<TestModel>
}