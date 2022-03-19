package com.example.menuapp.ui.main

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menuapp.R
import com.example.menuapp.base.BaseActivity
import com.example.menuapp.databinding.ActivityMainBinding
import com.example.menuapp.model.RestaurantsItem
import com.example.menuapp.ui.main.adapter.ItemAdapter
import com.example.menuapp.ui.main.adapter.ItemAdapterCallback
import com.example.menuapp.ui.restDetail.RestaurantDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), ItemAdapterCallback {


    override val viewModel: MainViewModel by viewModels()
    override fun layoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
        addListener()
    }

    private fun initUi() {
        binding.rvList.layoutManager = LinearLayoutManager(this)
        binding.rvList.adapter = ItemAdapter(this)

    }

    private fun addListener() {
        with(binding) {

            appBarLayout.imgSearch.setOnClickListener {
                appBarLayout.clNormal.visibility = View.GONE
                appBarLayout.clSearch.visibility = View.VISIBLE
            }
            appBarLayout.imgNormal.setOnClickListener {
                appBarLayout.clNormal.visibility = View.VISIBLE
                appBarLayout.clSearch.visibility = View.GONE
                viewModel.restoreList()
            }
            appBarLayout.imgCut.setOnClickListener {
                if (appBarLayout.etSearch.text.isNullOrBlank()) {
                    appBarLayout.clNormal.visibility = View.VISIBLE
                    appBarLayout.clSearch.visibility = View.GONE
                    viewModel.restoreList()
                    return@setOnClickListener
                }
                appBarLayout.etSearch.text?.clear()
            }
            appBarLayout.etSearch.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) = Unit
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) =
                    Unit

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    viewModel.searchList(binding.appBarLayout.etSearch.text.toString())
                }
            })

        }
    }


    override fun addObservers() {
        observeRestaurantList()
        observeSearchList()
    }

    private fun observeSearchList() {
        viewModel.searchResult.observe(this) {
            (binding.rvList.adapter as ItemAdapter).submitList(it)
        }
    }

    private fun observeRestaurantList() {
        viewModel.restaurantList.observe(this) {
            (binding.rvList.adapter as ItemAdapter).submitList(it)
        }
    }

    override fun onItemClick(restaurantsItem: RestaurantsItem) {
        val intent = Intent(this, RestaurantDetailActivity::class.java)
        intent.putExtra("restra", restaurantsItem)
        startActivity(intent)
    }

    override fun onBackPressed() {
        exitApp()
    }

    private fun exitApp() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Close App?")
        builder.setIcon(R.mipmap.ic_launcher)
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            val a = Intent(Intent.ACTION_MAIN)
            a.addCategory(Intent.CATEGORY_HOME)
            a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(a)
        }
        builder.setNegativeButton("No") { dialogInterface, which ->
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

}