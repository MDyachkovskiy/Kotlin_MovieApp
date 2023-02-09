package com.example.kotlin_movieapp.utils

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.Fragment
import com.example.kotlin_movieapp.R
import com.example.kotlin_movieapp.ui.main.MainActivity

fun View.openDetailsFragment (
    fragmentClass: Class<out Fragment>,
    KEY_BUNDLE_ITEM: String,
    item: Parcelable
){
    val fragment = fragmentClass.newInstance()
    fragment.arguments = Bundle().apply { putParcelable(KEY_BUNDLE_ITEM, item) }

    (context as MainActivity).supportFragmentManager
        .beginTransaction()
        .addToBackStack("")
        .add(R.id.main_container, fragment)
        .commit()
}