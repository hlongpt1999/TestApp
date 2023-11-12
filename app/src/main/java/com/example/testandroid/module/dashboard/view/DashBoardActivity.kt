package com.example.testandroid.module.dashboard.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.testandroid.databinding.ActivityDashBoardBinding
import com.example.testandroid.module.dashboard.model.DashBoardNavigation
import com.example.testandroid.module.dashboard.model.Navigation
import com.example.testandroid.module.dashboard.viewmodel.DashBoardViewModel

class DashBoardActivity : AppCompatActivity(), NavigationAdapter.IOnItemClickListener {

    private lateinit var binding: ActivityDashBoardBinding
    private val viewModel: DashBoardViewModel by viewModels()
        
    private val navigationList = listOf<Navigation>(
        Navigation(
            DashBoardNavigation.SETTINGS,
            "Settings",
            fragment = NewsFragment()
        ),
        Navigation(
            DashBoardNavigation.NEWS,
            "News",
            fragment = SettingFragment()
        ),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        addFragment(SettingFragment())
        binding.apply {
            rcvBottomNavigation.apply {
                adapter = NavigationAdapter(ArrayList(navigationList), this@DashBoardActivity)
                LinearSnapHelper().attachToRecyclerView(this)
            }
        }
    }

    private fun addFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.apply {
            add(binding.frameContentView.id, fragment)
            commit()
        }
    }

    override fun onItemClick(navigation: Navigation) {
        when (navigation.id) {
            DashBoardNavigation.NEWS -> {
                addFragment(NewsFragment())
            }
            DashBoardNavigation.SETTINGS -> {
                addFragment(SettingFragment())
            }
        }
    }
}