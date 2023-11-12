package com.example.testandroid.module.testroom.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.testandroid.MyApplication
import com.example.testandroid.databinding.ActivityRoomBinding
import com.example.testandroid.function.ToastUtils.showToastLongTime
import com.example.testandroid.module.testroom.model.Word
import com.example.testandroid.module.testroom.viewmodel.WordViewModel
import com.example.testandroid.module.testroom.viewmodel.WordViewModelFactory

class RoomActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRoomBinding
    private val wordAdapter = WordListAdapter()
    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                it.data?.getStringExtra(AddNewWordActivity.EXTRA_REPLY)?.let { reply ->
                    wordViewModel.inset(Word(reply))
                }
            } else {
                this.showToastLongTime("Empty Word")
            }
        }

    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((application as MyApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        initObservers()
        initEvent()
    }

    private fun initUI() {
        initAdapter()
    }

    private fun initAdapter() {
        binding.rcvWord.adapter = wordAdapter

    }

    private fun initObservers() {
        wordViewModel.allWords.observe(this) {
            it?.let {
                wordAdapter.submitList(it)
            }
        }
    }

    private fun initEvent() {
        binding.btnAdd.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.btnAdd -> {
                startForResult.launch(Intent(this, AddNewWordActivity::class.java))
            }
        }
    }

}