package com.example.kotlin_movieapp.utils

import android.Manifest
import android.content.pm.PackageManager
import android.view.View
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

fun checkPermission (
    permission: String,
    activity: FragmentActivity?,
    itemView: View
) : Boolean {
    val context = itemView.context
    val error = IllegalArgumentException("Некорректный запрос разрешения $permission")

    activity?.let {

        val shouldShowRationale = when (permission) {
            Manifest.permission.READ_CONTACTS
            -> shouldShowRequestPermissionRationale(it, Manifest.permission.READ_CONTACTS)

            Manifest.permission.CALL_PHONE
            -> shouldShowRequestPermissionRationale(it, Manifest.permission.CALL_PHONE)

            else -> error
        }

        val code = when (permission) {
            Manifest.permission.CALL_PHONE ->PHONE_CALL_REQUEST_CODE
            Manifest.permission.READ_CONTACTS -> CONTACTS_REQUEST_CODE
            else -> {error}
        }

        when {
            ContextCompat.checkSelfPermission(context, permission)
                    == PackageManager.PERMISSION_GRANTED -> {

                when (permission) {
                    Manifest.permission.READ_CONTACTS -> true

                    Manifest.permission.CALL_PHONE -> true

                    else -> {
                        throw error }
                }
            }

            shouldShowRationale as Boolean -> {
                when (permission) {
                    Manifest.permission.READ_CONTACTS -> {
                        itemView.showAlertMessage(code, activity)
                        return false
                    }


                    Manifest.permission.CALL_PHONE -> {
                        itemView.showAlertMessage(code, activity)
                        return false
                    }

                    else -> {
                        throw error }
                }
            }
            else -> {
                when (permission) {
                    Manifest.permission.READ_CONTACTS -> requestPermission(code, activity)

                    Manifest.permission.CALL_PHONE -> requestPermission(code, activity)

                    else -> {
                        throw error}
                }
            }
        }
    }
    return true
}