package com.example.menuapp.model

import java.io.Serializable


data class MenuItemsItem (
	val images: List<Any?>? = null,
	val price: String? = null,
	val name: String? = null,
	val description: String? = null,
	val id: String? = null
) : Serializable

data class CategoriesItem(
	val menuItems: List<MenuItemsItem?>? = null,
	val name: String? = null,
	val id: String? = null
): Serializable

data class MenusItem(
	val categories: List<CategoriesItem?>? = null,
	val restaurantId: Int? = null
): Serializable{

}

