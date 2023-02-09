package com.example.kotlin_movieapp.ui.main.AppState

import android.view.View
import com.example.kotlin_movieapp.R
import com.example.kotlin_movieapp.utils.showSnackBar

class AppStateRenderer (
    private val parentView: View,
    private val action: (View) -> Unit,
    ) {

    fun render(appState: AppState) {

        val loadingLayout = parentView.findViewById<View>(R.id.includedLoadingLayout)

        if (loadingLayout != null) {
            when (appState) {
                is AppState.Error -> {
                    loadingLayout.visibility = View.GONE
                    parentView.showSnackBar(
                        parentView.context.getString(R.string.data_loading_error),
                        parentView.context.getString(R.string.reload),
                        action, 0
                    )
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

            val loadingLayout = parentView.findViewById<View>(R.id.includedLoadingLayout)

            if (loadingLayout != null) {
                when (appState) {
                    is DetailsState.Error -> {
                        loadingLayout.visibility = View.GONE
                        parentView.showSnackBar(
                            parentView.context.getString(R.string.data_loading_error),
                            parentView.context.getString(R.string.reload),
                            action, 0
                        )
                    }
                    is DetailsState.Loading -> {
                        loadingLayout.visibility = View.VISIBLE
                    }
                    else -> {
                        loadingLayout.visibility = View.GONE
                        parentView.showSnackBar(
                            parentView.context.getString(R.string.data_loading_success), 0
                        )
                    }
                }
            }
        }
    }


