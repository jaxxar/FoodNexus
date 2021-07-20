package com.example.foodnexus.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.foodnexus.R
import com.example.foodnexus.databinding.DesignDishItemBinding
import com.example.foodnexus.model.DishesData

class DishAdapter(
    private val fragment: Fragment,
    private val callback: DishCallback,
    private val showEdit: Boolean
) :
    ListAdapter<DishesData, DishAdapter.ViewHolder>(DishDiffCallback()) {

    class ViewHolder(
        private val binding: DesignDishItemBinding,
        private val showEdit: Boolean,
        private val fragment: Fragment,
        private val callback: DishCallback
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dishesData: DishesData) {
            val circularProgressDrawable = CircularProgressDrawable(fragment.requireContext())
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            Glide.with(fragment)
                .load(dishesData.image)
                .placeholder(circularProgressDrawable)
                .into(binding.dishImage)
            binding.dishTitle.text = dishesData.title
            binding.root.tag = dishesData.id

            if (showEdit) {
                binding.editImage.visibility = View.VISIBLE
                binding.editImage.setOnClickListener {
                    val popUp = PopupMenu(fragment.context, binding.editImage)
                    popUp.menuInflater.inflate(R.menu.dish_options_menu, popUp.menu)
                    popUp.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.action_edit_dish -> {
                                callback.returnEditDish(dishesData)
                            }
                            R.id.action_delete_dish -> {
                                callback.returnDeleteDish(dishesData)
                            }
                        }
                        true
                    }
                    popUp.show()
                }
            } else {
                binding.editImage.visibility = View.GONE
            }
            binding.dishImage.setOnClickListener {
                callback.returnDetailsDish(dishesData)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            DesignDishItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, showEdit, fragment, callback)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    private class DishDiffCallback : DiffUtil.ItemCallback<DishesData>() {

        override fun areItemsTheSame(
            oldItem: DishesData,
            newItem: DishesData
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: DishesData,
            newItem: DishesData
        ): Boolean = oldItem == newItem
    }
}

interface DishCallback {
    fun returnEditDish(dish: DishesData)
    fun returnDeleteDish(dish: DishesData)
    fun returnDetailsDish(dish: DishesData)
}