package com.example.testandroid.module.dashboard.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testandroid.databinding.ItemNavigationDashBoardBinding
import com.example.testandroid.module.dashboard.model.Navigation

class NavigationAdapter(
    private var itemData: ArrayList<Navigation>,
    private val onItemClickListener: IOnItemClickListener
) : RecyclerView.Adapter<NavigationAdapter.NavigationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationViewHolder {
        val view = NavigationViewHolder(
            ItemNavigationDashBoardBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
        view.binding.root.layoutParams = ViewGroup.LayoutParams(
            (parent.width) * 1 / 5,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return view
    }

    override fun getItemCount(): Int = itemData.size

    override fun onBindViewHolder(holder: NavigationViewHolder, position: Int) {
        val item = itemData[position]
        holder.binding.tvLabel.apply {
            text = item.label
            setSingleLine()
            isSelected = true
        }
        holder.binding.root.setOnClickListener {
            onItemClickListener.onItemClick(item)
        }
    }

    class NavigationViewHolder(private val mBinding: ItemNavigationDashBoardBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        val binding = mBinding
    }

    interface IOnItemClickListener {
        fun onItemClick(navigation: Navigation)
    }
}