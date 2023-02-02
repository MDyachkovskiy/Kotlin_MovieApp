package com.example.kotlin_movieapp.ui.main.personDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_movieapp.model.personDetailsResponse.PersonDTO
import com.example.kotlin_movieapp.repository.RemoteDataSource
import com.example.kotlin_movieapp.repository.personDetails.PersonDetailsRepository
import com.example.kotlin_movieapp.repository.personDetails.PersonDetailsRepositoryImpl
import com.example.kotlin_movieapp.ui.main.DetailsState
import retrofit2.Call
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class PersonDetailsViewModel (
        private val liveData: MutableLiveData<DetailsState> = MutableLiveData(),
        private val personRepository: PersonDetailsRepository =
            PersonDetailsRepositoryImpl(RemoteDataSource())
) : ViewModel() {

    private val callback = object: retrofit2.Callback<PersonDTO> {
        override fun onResponse(
            call: Call<PersonDTO>,
            response: Response<PersonDTO>) {
            val serverResponse: PersonDTO? = response.body()
            liveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    DetailsState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<PersonDTO>, t: Throwable) {
            liveData.postValue(DetailsState.Error(Throwable(t.message?: REQUEST_ERROR)))
        }
    }

    private fun checkResponse(serverResponse: PersonDTO): DetailsState {
        return if (serverResponse == null) {
            DetailsState.Error(Throwable(CORRUPTED_DATA))
        } else {
            DetailsState.SuccessPerson(serverResponse)
        }
    }

    fun getLiveData() = liveData

    fun getPersonDetailsFromRemoteSource(personId: Int?) {
        liveData.value = DetailsState.Loading
        personRepository.getPersonDetailsFromRemoteServer(personId, callback)
    }

}