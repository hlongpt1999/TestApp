package com.example.testandroid

import android.app.Application
import android.content.Context

class MyApplication: Application() {

    companion object {
        private lateinit var appContext: Context

        fun getAppContext(): Context {
            return appContext
        }
    }
}