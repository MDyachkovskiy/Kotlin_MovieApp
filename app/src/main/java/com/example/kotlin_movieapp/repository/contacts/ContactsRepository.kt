package com.example.kotlin_movieapp.repository.contacts

import com.example.kotlin_movieapp.model.room.contacts.ContactsItem

interface ContactsRepository {
    fun getAllContacts() : List<ContactsItem>
    fun saveEntity(contact: ContactsItem)
}