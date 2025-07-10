package com.example.colorphone.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class ShimejiActions : Parcelable {
    JUMP,
    DRAG,
    SIT,
    FALLING,
    CLIMB,
    HANG,
}