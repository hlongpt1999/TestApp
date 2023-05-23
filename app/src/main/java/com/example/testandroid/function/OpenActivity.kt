package com.example.testandroid.function

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.testandroid.MainActivity
import com.example.testandroid.PipActivity

object OpenActivity {

    fun AppCompatActivity.openMainActivity() {
        this.startActivity(Intent(this, MainActivity::class.java))
    }

    fun AppCompatActivity.openPipActivity() {
        this.startActivity(Intent(this, PipActivity::class.java))
    }
}