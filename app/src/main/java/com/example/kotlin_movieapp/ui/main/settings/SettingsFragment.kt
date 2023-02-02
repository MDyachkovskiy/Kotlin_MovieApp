package com.example.kotlin_movieapp.ui.main.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.kotlin_movieapp.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}