package com.example.testandroid.module.security.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testandroid.R

class KeyboardAdapter(
    private val mContext: Context,
    private val list: ArrayList<String>,
    private val onItemClick: IOnClickListener
) : RecyclerView.Adapter<KeyboardAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_keyboard, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = list[position]
        holder.textView.setOnClickListener {
            onItemClick.onItemClick(list[position])
        }
    }

    override fun getItemId(p0: Int): Long = 0L
    override fun getItemCount(): Int = list.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            textView = view.findViewById(R.id.tvNumber)
        }
    }

    interface IOnClickListener {
        fun onItemClick(text: String)
    }
}