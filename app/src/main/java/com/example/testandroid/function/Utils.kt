package com.example.testandroid.function

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

object Utils {

    fun isCorrectEmail(email: String?): Boolean {
        return email == "admin"
    }

    fun getGoogleSignInClient(context: Context): GoogleSignInClient {
        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        return GoogleSignIn.getClient(context, gso)
    }

}