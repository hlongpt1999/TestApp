package com.example.testandroid

import android.app.Application
import android.content.Context
import com.example.testandroid.module.testroom.model.WordRepository
import com.example.testandroid.module.testroom.model.WordRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication : Application() {

    companion object {
        private lateinit var appContext: Context

        fun getAppContext(): Context {
            return appContext
        }
    }

    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { WordRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { WordRepository(database.wordDao()) }
}