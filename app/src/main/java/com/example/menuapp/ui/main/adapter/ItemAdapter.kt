package com.example.menuapp.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.menuapp.databinding.IndiviewItemBinding
import com.example.menuapp.model.RestaurantsItem

class ItemAdapter(private val callback: ItemAdapterCallback) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private val items = ArrayList<RestaurantsItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: ArrayList<RestaurantsItem>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }


    inner class ItemViewHolder(private val binding: IndiviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: RestaurantsItem) {
            with(binding) {
                tvResName.text = item.name
                tvRestAddress.text = item.address
                tvCuisine.text = item.cuisine_type
                cvMain.setOnClickListener {
                    callback.onItemClick(item)
                }
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemAdapter.ItemViewHolder {
        val binding =
            IndiviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemAdapter.ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}

interface ItemAdapterCallback {
    fun onItemClick(restaurantsItem: RestaurantsItem)
}
