package com.example.testandroid.module.dashboard.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.dgreenhalgh.android.simpleitemdecoration.grid.GridDividerItemDecoration
import com.example.testandroid.R
import com.example.testandroid.databinding.FragmentNewsBinding
import com.example.testandroid.module.dashboard.viewmodel.DashBoardViewModel

class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private val viewModel: DashBoardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initUI()
    }

    private fun initObserver() {
        viewModel.data.observe(this) {
            it?.let {
                if (it.isNotEmpty()) {
                    binding.tvTest.text = it
                }
            }
        }
    }

    private fun initUI() {
        val list = ArrayList<String>().apply {
            add("1")
            add("2")
            add("3")
            add("4")
            add("1")
            add("2")
            add("3")
            add("4")
        }
        binding.rcvView.apply {
            adapter = GridAdapter(context, list)
            layoutManager = GridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
        }
    }
}