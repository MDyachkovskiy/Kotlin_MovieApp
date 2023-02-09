package com.example.kotlin_movieapp.model.room.contacts

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import com.example.kotlin_movieapp.ui.main.contacts.ContactsViewModel

class ContactsGetter (
    private val context : Context?,
    private val viewModel: ContactsViewModel,
    ) {

    val contacts = mutableListOf<ContactsItem>()

    fun getContacts() {
        context?.let{

            Thread {
                val contentResolver : ContentResolver = it.contentResolver

                val cursorWithContacts : Cursor? = contentResolver.query (
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    null,
                    null,
                    ContactsContract.Contacts._ID + " ASC")

                cursorWithContacts?.let { cursor ->
                    for (i in 0 until cursor.count) {

                        val columnIdIndex = cursor.getColumnIndex(
                            ContactsContract
                            .CommonDataKinds
                            .Phone
                            ._ID)

                        val columnNameIndex =
                            cursor.getColumnIndex(
                                ContactsContract
                                .CommonDataKinds
                                .Phone
                                .DISPLAY_NAME)

                        val columnPhoneNumberIndex =
                            cursor.getColumnIndex(
                                ContactsContract
                                .CommonDataKinds
                                .Phone
                                .NUMBER)

                        if (cursor.moveToPosition(i)) {
                            val id = cursor.getString(columnIdIndex)
                            val name = cursor.getString(columnNameIndex)
                            val phoneNumber = cursor.getString(columnPhoneNumberIndex)
                            val contact = ContactsItem(id, name, phoneNumber)
                            contacts.add(contact)
                        }
                    }
                }
                viewModel.addAllContacts(contacts)
                cursorWithContacts?.close()
            }.start()
        }
    }
}