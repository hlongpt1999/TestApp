package com.example.testandroid

import android.app.PictureInPictureParams
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Rational
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView

class PipActivity : AppCompatActivity(), OnClickListener {
    var tvEnterPiP: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pip)
        initId()
        initClickEvent()
    }

    private fun initId() {
        tvEnterPiP = findViewById(R.id.tvEnterPiP)
    }

    private fun initClickEvent() {
        tvEnterPiP?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            tvEnterPiP -> {
                this.enterPictureInPictureMode(PictureInPictureParams.Builder()
                    .setAspectRatio(Rational(16,9))
                    .build()
                )
            }
        }
    }
}