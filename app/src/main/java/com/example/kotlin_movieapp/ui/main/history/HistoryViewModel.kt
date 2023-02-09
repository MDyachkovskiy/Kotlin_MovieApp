package com.example.kotlin_movieapp.ui.main.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_movieapp.App
import com.example.kotlin_movieapp.repository.history.LocalRepositoryImpl
import com.example.kotlin_movieapp.ui.main.AppState.AppState

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class HistoryViewModel(
    private val historyLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val historyRepository: LocalRepositoryImpl = LocalRepositoryImpl(App.getHistoryDao())
) : ViewModel() {

    fun getLiveData() = historyLiveData

    fun getAllHistory() {
        historyLiveData.postValue(AppState.Loading)
        historyLiveData.postValue(AppState.SuccessHistory(historyRepository.getAllHistory()))
    }
}