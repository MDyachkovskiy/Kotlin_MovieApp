package com.example.kotlin_movieapp.ui.main.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_movieapp.model.collectionResponse.SearchResponse
import com.example.kotlin_movieapp.repository.RemoteDataSource
import com.example.kotlin_movieapp.repository.search.SearchRepositoryImpl
import com.example.kotlin_movieapp.ui.main.AppState.AppState
import retrofit2.Call
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class SearchViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: SearchRepositoryImpl = SearchRepositoryImpl(RemoteDataSource()),
) : ViewModel() {

    fun getData(): LiveData<AppState> {
        return liveData
    }

    private val callback = object : retrofit2.Callback<SearchResponse> {
        override fun onResponse(
            call: Call<SearchResponse>,
            response: Response<SearchResponse>,
        ) {
            val serverResponse: SearchResponse? = response.body()
            liveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }
        override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
            liveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }
    }

        private fun checkResponse(serverResponse : SearchResponse) : AppState {
            return if (serverResponse == null) {
                AppState.Error(Throwable(CORRUPTED_DATA))
            } else {
                AppState.SuccessSearch(serverResponse)
            }
        }

    fun getAdultLatinSearchCollection(rating: Int?, name: String) {
        liveData.value = AppState.Loading
        repository.getAdultLatinSearchResultFromServer(rating, name, callback)
    }

    fun getLatinSearchCollection(rating: Int?, name: String) {
        liveData.value = AppState.Loading
        repository.getLatinSearchResultFromServer(rating, name, callback)
    }

    fun getAdultCyrillicSearchCollection(rating: Int?, name: String) {
        liveData.value = AppState.Loading
        repository.getAdultCyrillicSearchResultFromServer(rating, name, callback)
    }

    fun getCyrillicSearchCollection(rating: Int?, name: String) {
        liveData.value = AppState.Loading
        repository.getCyrillicSearchResultFromServer(rating, name, callback)
    }

}

