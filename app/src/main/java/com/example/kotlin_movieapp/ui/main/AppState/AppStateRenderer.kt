package com.example.kotlin_movieapp.ui.main.AppState

import android.view.View
import androidx.viewbinding.ViewBinding
import com.example.kotlin_movieapp.R

class AppStateRenderer (
    private val binding: ViewBinding
    ) {

    fun render(appState: AppState) {

        val loadingLayout = binding.root.findViewById<View>(R.id.includedLoadingLayout)

        if (loadingLayout != null) {
            when (appState) {
                is AppState.Error -> {
                    loadingLayout.visibility = View.GONE
                }
                is AppState.Loading -> {
                    loadingLayout.visibility = View.VISIBLE
                }
                else -> {
                    loadingLayout.visibility = View.GONE
                }
                }
            }
        }

    fun render(appState: DetailsState) {

        val loadingLayout = binding.root.findViewById<View>(R.id.includedLoadingLayout)

        if (loadingLayout != null) {
            when (appState) {
                is DetailsState.Error -> {
                    loadingLayout.visibility = View.GONE
                }
                is DetailsState.Loading -> {
                    loadingLayout.visibility = View.VISIBLE
                }
                else -> {
                    loadingLayout.visibility = View.GONE
                }
            }
        }
    }
}