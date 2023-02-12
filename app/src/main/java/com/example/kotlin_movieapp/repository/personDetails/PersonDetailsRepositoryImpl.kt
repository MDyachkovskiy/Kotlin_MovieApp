package com.example.kotlin_movieapp.repository.personDetails

import com.example.kotlin_movieapp.model.personDetailsResponse.PersonDTO
import com.example.kotlin_movieapp.repository.RemoteDataSource
import retrofit2.Callback

class PersonDetailsRepositoryImpl (
    private val remoteDataSource: RemoteDataSource) : PersonDetailsRepository {

    override fun getPersonDetailsFromRemoteServer(personId: Int?, callback: Callback<PersonDTO>) {
        remoteDataSource.getPersonDetails(personId, callback)
    }
}