package com.example.foodnexus.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodnexus.databinding.DesignListItemBinding

class ListItemAdapter(
    private val mContext: Context,
    private val list: List<String>,
    private val category: String,
    private val callback: SelectorCallback
) : RecyclerView.Adapter<ListItemAdapter.ViewHolder>() {

    private lateinit var binding: DesignListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DesignListItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item.text = list[position]
        holder.itemView.setOnClickListener { callback.returnSelection(list[position], category) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(binding: DesignListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val item: TextView = binding.listItem
    }
}

interface SelectorCallback {
    fun returnSelection(selection: String, category: String)
}