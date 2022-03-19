package com.example.menuapp.ui.restDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.menuapp.base.BaseViewModel
import com.example.menuapp.model.MenusItem
import com.example.menuapp.util.getMenuList
import com.example.menuapp.util.toLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    @Named("Menu") private val menuObject: JSONObject
) : BaseViewModel() {


    private var _menuList = MutableLiveData<ArrayList<MenusItem>>()
    val menuList = _menuList.toLiveData()

    private var _menuItem = MutableLiveData<MenusItem>()
    val menuItem = _menuItem.toLiveData()

    init {
        setMenuList()
    }


    private fun setMenuList() {
        launch {
            _menuList.postValue(getMenuList(menuObject.getJSONArray("menus")))
        }
    }

    fun searchMenuByRestaurantId(restraId: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            menuList.value?.forEach {
                if (restraId == it.restaurantId) {
                    _menuItem.postValue(it)
                    return@forEach
                }
            }
        }
    }


}