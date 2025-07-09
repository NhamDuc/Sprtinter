package com.example.colorphone.model

import androidx.annotation.DrawableRes

data class ShimejiItem(
    val id: Int?,
    val state: ShimejiState,
    val name: String,
    @DrawableRes
    val iconResId: Int? = null
)

sealed interface ShimejiState {
    data class IsDownloaded(var isSelected: Boolean = false, val details: ShimejiDetails) : ShimejiState
    object IsNotDownloaded : ShimejiState
}