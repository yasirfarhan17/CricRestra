package com.example.menuapp.ui.restDetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menuapp.R
import com.example.menuapp.base.BaseActivity
import com.example.menuapp.databinding.ActivityRestDetailBinding
import com.example.menuapp.model.CategoriesItem
import com.example.menuapp.model.RestaurantsItem
import com.example.menuapp.ui.restDetail.adapter.MenuItemAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantDetailActivity : BaseActivity<ActivityRestDetailBinding, RestaurantViewModel>() {


    override val viewModel: RestaurantViewModel by viewModels()
    override fun layoutId(): Int = R.layout.activity_rest_detail

    private var restaurantItem: RestaurantsItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
        initUi()
    }

    private fun getData() {
        if (intent.getParcelableExtra<RestaurantsItem>("restra") != null) {
            restaurantItem = intent.getParcelableExtra<RestaurantsItem>("restra")
            viewModel.searchMenuByRestaurantId(restaurantItem?.id!!)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initUi() {
        binding.tvResName.text = restaurantItem?.name ?: "--"
        binding.tvRestAddress.text = "Address:-${restaurantItem?.address}"
        binding.tvCuisine.text = restaurantItem?.cuisine_type
        binding.rvMenu.layoutManager = GridLayoutManager(this,2)
        binding.rvMenu.adapter = MenuItemAdapter()

        binding.back.setOnClickListener {
            onBackPressed()
        }

    }


    override fun addObservers() {
        // observeMenuItem()
        observeMenuList()
    }

    private fun observeMenuList() {
        viewModel.menuList.observe(this) { menuList ->
            showProgress()
            menuList?.forEach {
                if (restaurantItem?.id == it.restaurantId) {
                    (binding.rvMenu.adapter as MenuItemAdapter).submitList(it.categories as ArrayList<CategoriesItem>)
                    hideProgress()
                    return@forEach
                }
            }
            hideProgress()
        }
    }

}