package com.example.testandroid.module.dashboard.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.testandroid.R
import com.example.testandroid.databinding.ItemGridBinding
import com.example.testandroid.databinding.ItemNavigationDashBoardBinding
import com.example.testandroid.module.dashboard.model.Navigation

class GridAdapter(
    private var context: Context,
    private var itemData: ArrayList<String>
) : RecyclerView.Adapter<GridAdapter.GridViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val view = GridViewHolder(
            ItemGridBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
        return view
    }

    override fun getItemCount(): Int = itemData.size

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val item = itemData[position]
        holder.binding.tvItem.setBackgroundColor(
            if (position % 2 == 0) {
                ContextCompat.getColor(context, R.color.green_blue)
            } else {
                ContextCompat.getColor(context, R.color.green)
            }
        )
    }

    class GridViewHolder(private val mBinding: ItemGridBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        val binding = mBinding
    }

}