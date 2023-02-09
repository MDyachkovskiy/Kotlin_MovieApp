package com.example.kotlin_movieapp.ui.main.favoritesList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_movieapp.App
import com.example.kotlin_movieapp.repository.favorites.FavoritesRepositoryImpl
import com.example.kotlin_movieapp.ui.main.AppState.AppState

class FavoritesViewModel(
    private val favoritesLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val favoritesRepository: FavoritesRepositoryImpl =
        FavoritesRepositoryImpl(App.getFavoriteDao())
) : ViewModel() {

    fun getLiveData() = favoritesLiveData

    fun getAllFavorites() {
        favoritesLiveData.postValue(AppState.Loading)
        favoritesLiveData.postValue(AppState.SuccessFavorites(favoritesRepository.getAllFavorites()))
    }
}