package com.example.menuapp.ui.restDetail.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.menuapp.databinding.IndiviewMenuListsBinding
import com.example.menuapp.model.MenuItemsItem

class ChildMenuItemAdapter : RecyclerView.Adapter<ChildMenuItemAdapter.MenuItemViewHolder>() {

    private val items = ArrayList<MenuItemsItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: ArrayList<MenuItemsItem>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }


    inner class MenuItemViewHolder(private val binding: IndiviewMenuListsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: MenuItemsItem) {
            with(binding) {
                tvMenuPrice.text = "₹"+item.price
                tvMenuName.text = item.name
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuItemViewHolder {
        val binding =
            IndiviewMenuListsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}
