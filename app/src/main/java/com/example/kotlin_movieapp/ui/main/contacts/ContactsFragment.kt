package com.example.kotlin_movieapp.ui.main.contacts

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_movieapp.adapters.ContactsAdapter
import com.example.kotlin_movieapp.databinding.FragmentContactsBinding
import com.example.kotlin_movieapp.model.room.contacts.ContactsGetter
import com.example.kotlin_movieapp.model.room.contacts.ContactsItem
import com.example.kotlin_movieapp.ui.main.AppState.AppState
import com.example.kotlin_movieapp.ui.main.AppState.AppStateRenderer
import com.example.kotlin_movieapp.utils.*

class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!

    private lateinit var parentView: View

    private val dataRenderer by lazy {
        AppStateRenderer(parentView) {viewModel.getAllContacts()}
    }
    private val contactsGetter by lazy {ContactsGetter(context, viewModel)}

    private val viewModel: ContactsViewModel by lazy {
        ViewModelProvider (this)[ContactsViewModel::class.java]
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

        parentView = binding.contactsFragment

        viewModel.getLiveData().observe(viewLifecycleOwner) {
            renderData(it)
        }

        if (checkPermission(Manifest.permission.READ_CONTACTS, activity, binding.root)) {
            getContacts()}

        Thread {
            viewModel.getAllContacts()
        }.start()
    }

    private fun renderData(appState: AppState) {
        dataRenderer.render(appState)

        when(appState) {
            is AppState.SuccessContacts -> {
                initRV (appState.contactsData)
            }
            else -> return
        }
    }

    private fun initRV(contactsData: List<ContactsItem>) {
        binding.contactsRecyclerView.init(ContactsAdapter(contactsData, activity),
            LinearLayoutManager.VERTICAL)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CONTACTS_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getContacts()

                } else {
                    binding.contactsFragment.showAlertMessage(CONTACTS_REQUEST_CODE, activity)
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getContacts() {
        contactsGetter.getContacts()
    }
}