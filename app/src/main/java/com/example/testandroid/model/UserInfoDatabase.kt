package com.example.testandroid.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserInfoDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}