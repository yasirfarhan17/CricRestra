package com.example.menuapp.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.menuapp.base.BaseViewModel
import com.example.menuapp.model.MenusItem
import com.example.menuapp.model.RestaurantsItem
import com.example.menuapp.util.getMenuList
import com.example.menuapp.util.getRestaurantList
import com.example.menuapp.util.toLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainViewModel @Inject constructor(
    @Named("Restra") private val restraObject: JSONObject,
    @Named("Menu") private val menuObject: JSONObject
) : BaseViewModel() {


    private var _restaurantList = MutableLiveData<ArrayList<RestaurantsItem>>()
    val restaurantList = _restaurantList.toLiveData()
    private var _searchResult = MutableLiveData<ArrayList<RestaurantsItem>>()
    val searchResult = _searchResult.toLiveData()
    private var _menuList = MutableLiveData<ArrayList<MenusItem>>()
    val menuList = _menuList.toLiveData()

    init {
        setRestaurantList()
        setMenuList()
    }

    private fun setRestaurantList() {
        launch {
            _restaurantList.value?.clear()
            _restaurantList.postValue(getRestaurantList(restraObject.getJSONArray("restaurants")))
        }
    }

    private fun setMenuList() {
        launch {
            _menuList.postValue(getMenuList(menuObject.getJSONArray("menus")))
        }
    }

    fun searchList(name: String) {
        viewModelScope.launch(Dispatchers.Default) {
            searchItemInList(name)
        }
    }

    private suspend fun searchItemInList(name: String) = withContext(Dispatchers.Main) {
        val localList = ArrayList<RestaurantsItem>()
        restaurantList.value?.forEach { singleRestaurants ->
            if (singleRestaurants.name?.contains(name, true) == true) {
                localList.add(singleRestaurants)
            }
        }
        if (localList.isEmpty().not()) {
            _searchResult.postValue(localList)
            return@withContext
        }
        restaurantList.value?.forEach { singleRestaurants ->
            if (singleRestaurants.cuisine_type?.contains(name, true) == true) {
                localList.add(singleRestaurants)
            }
        }
        if (localList.isEmpty().not()) {
            _searchResult.postValue(localList)
            return@withContext
        }
        menuList.value?.forEach {
            it.categories?.forEach outerLoop@ { category ->
                if (category?.name?.contains(name, true) == true) {
                    it.restaurantId?.let { it1 -> getRestaurantsFromId(it1) }
                        ?.let { it2 -> localList.add(it2) }
                    return@outerLoop
                }
                category?.menuItems?.forEach innerLoop@{ menuItems ->
                    if (menuItems?.name?.contains(name, true) == true) {
                        it.restaurantId?.let { it1 -> getRestaurantsFromId(it1) }
                            ?.let { it2 -> localList.add(it2) }
                        return@outerLoop
                    }
                }
            }
        }
        _searchResult.postValue(localList)
        return@withContext
    }

    private fun getRestaurantsFromId(restaurantId: Int): RestaurantsItem? {
        restaurantList.value?.forEach {
            if (it.id == restaurantId) {
                return it
            }
        }
        return null
    }

    fun restoreList() {
        setRestaurantList()
    }

}