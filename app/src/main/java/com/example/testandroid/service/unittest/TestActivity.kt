package com.example.testandroid.service.unittest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testandroid.databinding.ActivityTestBinding
import com.example.testandroid.function.OpenActivity.openPipActivity
import com.example.testandroid.function.Utils

class TestActivity : AppCompatActivity() {

    private var binding: ActivityTestBinding ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.tvLogin?.setOnClickListener {
            login()
        }
    }

    private fun login() {
        binding?.apply {
            if (Utils.isCorrectEmail(edtEmail.text.toString()) && edtPassword.text.toString() == "admin") {
                this@TestActivity.openPipActivity()
            }
        }
    }
}