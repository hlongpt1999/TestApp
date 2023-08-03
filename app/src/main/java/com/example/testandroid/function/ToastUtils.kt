package com.example.testandroid.function

import android.app.Activity
import android.content.Context
import android.widget.Toast

object ToastUtils {

    fun Context.showToastLongTime(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun showToastLongTime(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun Context.showToastShortTime(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showToastShortTime(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}