package com.example.foodnexus.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodnexus.databinding.DesignInstructionsItemBinding
import com.example.foodnexus.model.RandomDishData

class InstructionsAdapter(
    private val mContext: Context,
    private val list: List<RandomDishData.Step>
) : RecyclerView.Adapter<InstructionsAdapter.ViewHolder>() {

    private lateinit var binding: DesignInstructionsItemBinding

    class ViewHolder(binding: DesignInstructionsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val instructionsText: TextView = binding.instructionsText
        val number: TextView = binding.stepNumberText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            DesignInstructionsItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return InstructionsAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.number.text = list[position].number.toString()
        holder.instructionsText.text = list[position].step
    }

    override fun getItemCount(): Int {
        return list.size
    }
}