package com.example.kotlin_movieapp.ui.main.contacts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_movieapp.App
import com.example.kotlin_movieapp.model.room.contacts.ContactsItem
import com.example.kotlin_movieapp.repository.contacts.ContactsRepositoryImpl
import com.example.kotlin_movieapp.ui.main.AppState.AppState

class ContactsViewModel(
    private val contactsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val contactsRepository: ContactsRepositoryImpl =
        ContactsRepositoryImpl(App.getContactsDao())
) : ViewModel() {

    fun getLiveData() = contactsLiveData

    fun getAllContacts() {
        contactsLiveData.postValue(AppState.Loading)
        contactsLiveData.postValue(AppState.SuccessContacts(contactsRepository.getAllContacts()))
    }

    fun addAllContacts(contacts: MutableList<ContactsItem>){
        contacts.forEach { entity -> contactsRepository.saveEntity(entity) }
    }
}