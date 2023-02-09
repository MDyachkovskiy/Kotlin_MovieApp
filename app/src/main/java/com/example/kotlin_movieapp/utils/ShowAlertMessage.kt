package com.example.kotlin_movieapp.utils

import android.app.AlertDialog
import android.view.View
import androidx.fragment.app.FragmentActivity

fun View.showAlertMessage(
    code: java.io.Serializable,
    activity: FragmentActivity?
) {
    context.let {
        when(code) {
            PHONE_CALL_REQUEST_CODE ->
                AlertDialog.Builder(it)
                    .setTitle("Доступ к звонкам")
                    .setMessage("Для осуществления звонков необходимо предоставить доступ")
                    .setPositiveButton("Предоставить доступ") { _, _ ->
                        requestPermission(code, activity)
                    }
                    .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
                    .create()
                    .show()

                CONTACTS_REQUEST_CODE ->
                    AlertDialog.Builder(it)
                        .setTitle("Доступ к контактам")
                        .setMessage("Для отображения контактов необходимо предоставить доступ")
                        .setPositiveButton("Предоставить доступ") { _, _ ->
                            requestPermission(code, activity)
                        }
                        .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
                        .create()
                        .show()
        }
    }
}