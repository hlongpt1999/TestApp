package com.example.testandroid.module.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testandroid.model.FirebaseChild
import com.example.testandroid.model.User
import com.example.testandroid.model.UserDao
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val saveUserToFirebaseLiveData = MutableLiveData<Boolean>().apply { null }
    var userDao: UserDao? = null

    fun saveUserToFirebaseDatabase(account: GoogleSignInAccount) {
        viewModelScope.launch {
            val map = HashMap<String, Any>().apply {
                put("id", account.id ?: "")
                put("name", account.displayName ?: "")
                put("avatarUrl", account.photoUrl ?: "")
            }
            FirebaseDatabase.getInstance().reference
                .child(FirebaseChild.USER.value)
                .child(account.id ?: "errorChild")
                .setValue(map)
            saveUserToFirebaseLiveData.postValue(true)
        }
    }

    fun saveToDatabase(user: User) {
        viewModelScope.launch {
            userDao?.insertAll(user)
        }
    }
}