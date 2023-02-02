package com.example.kotlin_movieapp.utils

import android.view.View
import android.widget.Toast


fun View.showToast(text: String) {
    Toast.makeText(this.context, text, Toast.LENGTH_SHORT).show()
}
