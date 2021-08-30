package com.viled.feature_job

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Job(
    val id: String,
    val company: Company,
    val salary: String,
    val location: String,
    val colleges: List<College>,
    val positionName: String,
    val positionDescription: String,
    val isActive: Boolean
) : Parcelable

@Parcelize
data class Company(
    val name: String = "Tinkoff",
    val established: String = "2001",
    val employees: String = "120",
    val headquarterLocation: String = "Moscow, Russia",
    val slides: List<String>,
    val website: String = "",
    val description: String = ""
) : Parcelable

@Parcelize
data class College(
    val name: String,
    val position: String,
    val experience: String,
    val photo: String
) : Parcelable