package com.example.menuapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class OperatingHours(
    val Monday: String? = null,
    val Thursday: String? = null,
    val Friday: String? = null,
    val Sunday: String? = null,
    val Wednesday: String? = null,
    val Tuesday: String? = null,
    val Saturday: String? = null
) : Parcelable

@Parcelize
data class Latlng(
    val lng: Double? = null,
    val lat: Double? = null
) : Parcelable

@Parcelize
data class ReviewsItem(
    val date: String? = "null",
    val comments: String? = "null",
    val name: String? = "null",
    val rating: Int? = 0
) : Parcelable

@Parcelize
data class RestaurantsItem(
    val photograph: String? = null,
    val address: String? = null,
    val reviews: List<ReviewsItem?>? = emptyList<ReviewsItem>(),
    val operating_hours: OperatingHours? = OperatingHours(),
    val name: String? = null,
    val id: Int? = null,
    val neighborhood: String? = null,
    val cuisine_type: String? = null,
    val latlng: Latlng? = Latlng()
) : Parcelable
