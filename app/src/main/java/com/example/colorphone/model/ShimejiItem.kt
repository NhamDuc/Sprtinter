package com.example.colorphone.model

import androidx.annotation.DrawableRes

data class ShimejiItem(
    val id: Int,
    val isSelected: Boolean,
    val name: String,
    @DrawableRes
    val iconResId: Int? = null
)