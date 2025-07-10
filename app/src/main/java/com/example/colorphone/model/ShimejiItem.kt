package com.example.colorphone.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShimejiItem(
    val id: Int?,
    val state: ShimejiState,
    val name: String,
    @DrawableRes
    val iconResId: Int? = null
) : Parcelable

@Parcelize
sealed interface ShimejiState : Parcelable{
    data class IsDownloaded(var isSelected: Boolean = false, val details: ShimejiDetails) : ShimejiState
    object IsNotDownloaded : ShimejiState
}