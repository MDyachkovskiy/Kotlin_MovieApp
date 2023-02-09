package com.example.kotlin_movieapp.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun FragmentManager.replaceFragment(
    container: Int,
    fragment: Fragment
) {
    beginTransaction()
        .replace(container, fragment)
        .addToBackStack("")
        .commit()
}