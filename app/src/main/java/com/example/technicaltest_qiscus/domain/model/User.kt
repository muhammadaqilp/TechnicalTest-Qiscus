package com.example.technicaltest_qiscus.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: String,
    val name: String,
    val avatar: String
) : Parcelable, Comparable<User> {
    override fun compareTo(other: User): Int {
        return name.compareTo(other.name)
    }
}
