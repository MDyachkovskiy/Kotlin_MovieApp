package com.example.kotlin_movieapp.model.room.contacts

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ContactsItem(
    var id: String = "",
    var name: String? = "",
    var phoneNumber: String? = ""
) : Parcelable
