package com.example.testandroid.module.testroom.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.testandroid.databinding.ActivityAddNewWordBinding

class AddNewWordActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAddNewWordBinding

    companion object {
        const val EXTRA_REPLY = "om.example.testandroid.module.testroom.view.REPLY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewWordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initEvent()
    }

    private fun initEvent() {
        binding.buttonSave.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.buttonSave -> {
                val replyIntent = Intent()
                if (TextUtils.isEmpty(binding.edtAddWord.text)) {
                    setResult(Activity.RESULT_CANCELED, replyIntent)
                } else {
                    val word = binding.edtAddWord.text.toString()
                    replyIntent.putExtra(EXTRA_REPLY, word)
                    setResult(Activity.RESULT_OK, replyIntent)
                }
                finish()
            }
        }
    }
}