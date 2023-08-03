package com.example.testandroid.function

import android.content.res.Resources
import androidx.annotation.StringRes
import com.example.testandroid.MainActivity
import com.example.testandroid.MyApplication
import com.example.testandroid.R
import java.util.Objects

object Messages {

    private fun getString(@StringRes id: Int, vararg formatArgs: Objects): String {
        return MyApplication.getAppContext().resources.getString(id, formatArgs)
    }

    fun getMessageFromStatusCode(): String {
        return when {
            else -> getString(R.string.msg_default_message)
        }
    }
}