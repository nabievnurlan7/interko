package com.viled.core.dto

import android.os.Parcelable
import com.viled.core.common.SHORT_INTERVIEW
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Mode(
    val isRealInterview: Boolean = false,
    val quantity: Int = SHORT_INTERVIEW,
    val subjectId: Int = 0
) : Parcelable