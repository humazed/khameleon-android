package com.pandulapeter.khameleon.data.model

import android.os.Parcelable
import com.pandulapeter.khameleon.util.forceCapitalize
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: String = "",
    val name: String = "",
    val avatar: String? = null
) : Parcelable {

    fun getFormattedName() = name.forceCapitalize()
}