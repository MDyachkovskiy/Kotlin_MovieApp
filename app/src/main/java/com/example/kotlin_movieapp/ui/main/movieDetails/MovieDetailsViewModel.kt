package com.example.kotlin_movieapp.ui.main.movieDetails

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_movieapp.App
import com.example.kotlin_movieapp.model.movieDetailsResponse.MovieDTO
import com.example.kotlin_movieapp.repository.movieDetails.DetailsRepository
import com.example.kotlin_movieapp.repository.movieDetails.DetailsRepositoryImpl
import com.example.kotlin_movieapp.repository.RemoteDataSource
import com.example.kotlin_movieapp.repository.favorites.FavoritesRepositoryImpl
import com.example.kotlin_movieapp.repository.history.LocalRepositoryImpl
import com.example.kotlin_movieapp.ui.main.DetailsState

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class MovieDetailsViewModel(
    private val liveData: MutableLiveData<DetailsState> = MutableLiveData(),
    private val detailsRepository: DetailsRepository = DetailsRepositoryImpl(RemoteDataSource()),
    private val historyRepository: LocalRepositoryImpl = LocalRepositoryImpl(App.getHistoryDao()),
    private val favoriteRepository: FavoritesRepositoryImpl = FavoritesRepositoryImpl(App.getFavoriteDao())
) : ViewModel() {

    private val callback = object : retrofit2.Callback<MovieDTO> {

        override fun onFailure(call: retrofit2.Call<MovieDTO>, t: Throwable) {
            liveData.postValue(DetailsState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }

        override fun onResponse(
            call: retrofit2.Call<MovieDTO>,
            response: retrofit2.Response<MovieDTO>,
        ) {
            val serverResponse : MovieDTO? = response.body()
            liveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    DetailsState.Error(Throwable(SERVER_ERROR))
                }
            )
        }
    }

    private fun checkResponse (serverResponse: MovieDTO) : DetailsState{

        return if (serverResponse == null) {
            DetailsState.Error(Throwable(CORRUPTED_DATA))
        } else {
            DetailsState.Success(serverResponse)
        }
    }

    fun getLiveData() = liveData

    fun getMovieFromRemoteSource(movieId: Int?) {
        liveData.value = DetailsState.Loading
        detailsRepository.getMovieDetailsFromServer(movieId, callback)
    }

    fun saveMovieToDB (movieDTO: MovieDTO, date: Long) {
        historyRepository.saveEntity(movieDTO, date)
    }

    fun saveFavoriteMovieToDB (movieDTO: MovieDTO) {
        favoriteRepository.saveEntity(movieDTO)
    }

    fun deleteFavoriteMovieFromDB (movieDTO: MovieDTO) {
        favoriteRepository.deleteEntity(movieDTO)
    }

    fun addCommentToMovie (movieDTO: MovieDTO, text: Editable?) {
        historyRepository.addUserComment(movieDTO,text)
    }
}