package com.example.testandroid.module.flash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testandroid.databinding.ActivityFlashBinding


class FlashActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFlashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFlashBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}