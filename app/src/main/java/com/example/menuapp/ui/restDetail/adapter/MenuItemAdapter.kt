package com.example.menuapp.ui.restDetail.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.menuapp.databinding.IndiviewMenuBinding
import com.example.menuapp.model.CategoriesItem
import com.example.menuapp.model.MenuItemsItem

class MenuItemAdapter : RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder>() {

    private val items = ArrayList<CategoriesItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: ArrayList<CategoriesItem>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }


    inner class MenuItemViewHolder(private val binding: IndiviewMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: CategoriesItem) {
            with(binding) {
                tvCategaoryName.text = item.name
                rvMenuList.layoutManager = LinearLayoutManager(itemView.context)
                rvMenuList.adapter = ChildMenuItemAdapter()
                (binding.rvMenuList.adapter as ChildMenuItemAdapter).submitList(item.menuItems as ArrayList<MenuItemsItem>)
                /* if (item.menuItems.isNullOrEmpty().not()) {
                     for (menuItem in item.menuItems!!) {
                         val subBinding = DataBindingUtil.inflate<IndiviewMenuListsBinding>(
                             LayoutInflater.from(binding.root.context),
                             R.layout.indiview_menu_lists,
                             null,
                             false
                         )
                         subBinding.tvMenuName.text = menuItem?.name
                         subBinding.tvMenuPrice.text = menuItem?.price
                         //binding.executePendingBindings()
                         binding.llMenuList.addView(subBinding.root)
                     }

                 }*/

            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuItemViewHolder {
        val binding =
            IndiviewMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}
