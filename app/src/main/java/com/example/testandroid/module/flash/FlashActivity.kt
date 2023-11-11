package com.example.testandroid.module.flash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.testandroid.databinding.ActivityFlashBinding
import com.example.testandroid.function.GlobalData
import com.example.testandroid.function.OpenActivity.open
import com.example.testandroid.model.FirebaseChild
import com.example.testandroid.model.ScreenEnum
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class FlashActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFlashBinding
    private val firebaseListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val url = snapshot.value
            GlobalData.appConfig.backgroundUrl = url.toString()
        }

        override fun onCancelled(error: DatabaseError) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFlashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getConfigFromFirebase()

        Handler(mainLooper).postDelayed({
            this@FlashActivity.open(ScreenEnum.MAIN)
        }, 2000)
    }

    private fun getConfigFromFirebase() {
        GlobalScope.launch(Dispatchers.Main) {
            FirebaseDatabase.getInstance().reference
                .child(FirebaseChild.CONFIG.value)
                .child(FirebaseChild.CONFIG_BACKGROUND.value)
                .addValueEventListener(firebaseListener)
        }
    }
}