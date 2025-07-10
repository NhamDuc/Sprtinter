package com.example.colorphone.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShimejiDetails(
    val shimejiActions: List<ShimejiActions>
) : Parcelable