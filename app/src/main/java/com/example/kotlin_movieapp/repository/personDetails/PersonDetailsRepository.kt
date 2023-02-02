package com.example.kotlin_movieapp.repository.personDetails

import com.example.kotlin_movieapp.model.personDetailsResponse.PersonDTO

interface PersonDetailsRepository {
    fun getPersonDetailsFromRemoteServer (personId : Int?, callback: retrofit2.Callback<PersonDTO>)
}