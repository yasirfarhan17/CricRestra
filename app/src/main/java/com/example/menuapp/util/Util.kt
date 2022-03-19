package com.example.menuapp.util

import android.content.Context
import com.example.menuapp.model.MenusItem
import com.example.menuapp.model.RestaurantsItem
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

fun Context.loadJSONFromAssets(fileName: String): String {
    return applicationContext.assets.open(fileName).bufferedReader().use { reader ->
        reader.readText()
    }
}

fun getRestaurantList(jsonArray: JSONArray): ArrayList<RestaurantsItem> {
    val restaurantList = ArrayList<RestaurantsItem>()
    for (i in 0 until jsonArray.length()) {
        val student: RestaurantsItem =
            Gson().fromJson(jsonArray.get(i).toString(), RestaurantsItem::class.java)
        restaurantList.add(student)
    }
    return restaurantList

}

fun getMenuList(jsonArray: JSONArray): ArrayList<MenusItem> {
    val menuList = ArrayList<MenusItem>()
    for (i in 0 until jsonArray.length()) {
        val student: MenusItem =
            Gson().fromJson(jsonArray.get(i).toString(), MenusItem::class.java)
        menuList.add(student)
    }
    return menuList
}

