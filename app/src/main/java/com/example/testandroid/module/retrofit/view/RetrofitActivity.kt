package com.example.testandroid.module.retrofit.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.testandroid.R
import com.example.testandroid.databinding.ActivityRetrofitBinding
import com.example.testandroid.module.retrofit.viewmodel.TestViewModel

class RetrofitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRetrofitBinding
    private lateinit var mViewModel: TestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_retrofit)
        mViewModel = ViewModelProvider(this)[TestViewModel::class.java]
        binding.apply {
            viewModel = mViewModel
            lifecycleOwner = this@RetrofitActivity
        }
        initViewModel()
        initAPIResponse()
    }

    private fun initViewModel() {
        mViewModel.getTestApi()
    }

    private fun initAPIResponse() {
        mViewModel.apply {
            testLiveData.observe(this@RetrofitActivity) {
                it?.let {
                    Toast.makeText(
                        this@RetrofitActivity,
                        "Success = $it",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}