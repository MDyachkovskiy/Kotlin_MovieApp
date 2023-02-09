package com.example.kotlin_movieapp.utils

import android.Manifest
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity

const val PHONE_CALL_REQUEST_CODE = 23
const val CONTACTS_REQUEST_CODE = 42

fun requestPermission(
    code: java.io.Serializable,
    activity: FragmentActivity?
) {
    activity?.let {
        when (code) {
            PHONE_CALL_REQUEST_CODE ->
                ActivityCompat.requestPermissions (it, arrayOf(Manifest.permission.CALL_PHONE),
                    PHONE_CALL_REQUEST_CODE)

            CONTACTS_REQUEST_CODE ->
                ActivityCompat.requestPermissions(it, arrayOf(Manifest.permission.READ_CONTACTS),
                    CONTACTS_REQUEST_CODE)
        }
    }
}