package com.example.kotlin_movieapp.ui.main.contacts

import android.Manifest
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_movieapp.adapters.ContactsAdapter
import com.example.kotlin_movieapp.databinding.FragmentContactsBinding
import com.example.kotlin_movieapp.model.room.contacts.ContactsItem
import com.example.kotlin_movieapp.ui.main.AppState

const val REQUEST_CODE = 42

class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ContactsViewModel by lazy {
        ViewModelProvider (this).get(ContactsViewModel::class.java)
    }

    companion object {
        fun newInstance() = ContactsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermission()

        Thread {
            viewModel.getAllContacts()
        }.start()

        viewModel.getLiveData().observe(viewLifecycleOwner, Observer{
            renderData(it)
        })

    }

    private fun renderData(appState: AppState) {
        when(appState) {
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.SuccessContacts -> {
                binding.loadingLayout.visibility = View.GONE
                initRV (appState.contactsData)
            }
            else -> return
        }
    }

    private fun initRV(contactsData: List<ContactsItem>) {
        binding.contactsRecyclerView.apply{
            adapter = ContactsAdapter(contactsData, activity)
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    private fun checkPermission() {
        context?.let {

            when {
                ContextCompat.checkSelfPermission(it, Manifest.permission.READ_CONTACTS) 
                        == PackageManager.PERMISSION_GRANTED -> {
                        getContacts()
                }

                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    showAlertMessage()
                }
                else -> {
                    requestPermission()
                }
            }
        }
    }

    private fun getContacts() {
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

                        val columnIdIndex = cursor.getColumnIndex(ContactsContract
                            .CommonDataKinds
                            .Phone
                            ._ID)

                        val columnNameIndex =
                            cursor.getColumnIndex(ContactsContract
                                .CommonDataKinds
                                .Phone
                                .DISPLAY_NAME)

                        val columnPhoneNumberIndex =
                            cursor.getColumnIndex(ContactsContract
                                .CommonDataKinds
                                .Phone
                                .NUMBER)

                        if (cursor.moveToPosition(i)) {
                            val id = cursor.getString(columnIdIndex)
                            val name = cursor.getString(columnNameIndex)
                            val phoneNumber = cursor.getString(columnPhoneNumberIndex)
                            val contact = ContactsItem(id, name, phoneNumber)
                            viewModel.addContact(contact)
                        }
                    }
                }
                cursorWithContacts?.close()
            }.start()
        }
    }

    private fun requestPermission(){
        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getContacts()
                } else {
                    showAlertMessage()
                }
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun showAlertMessage() {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle("Доступ к контактам")
                .setMessage("Bla bla Объяснение зачем")
                .setPositiveButton("Предоставить доступ") { _, _ ->
                    requestPermission()
                }
                .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }

    private fun makePhoneCall(phoneNumber : String) {
        if (phoneNumber.isNotEmpty()){
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:$phoneNumber")
            startActivity(callIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}