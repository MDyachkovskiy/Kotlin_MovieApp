package com.example.kotlin_movieapp.adapters

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_movieapp.databinding.ItemContactsBinding
import com.example.kotlin_movieapp.model.room.contacts.ContactsItem

const val REQUEST_CODE = 23

class ContactsAdapter (
    private var contactsData: List<ContactsItem>,
    private var activity: FragmentActivity?
        ) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemContactsBinding.inflate(LayoutInflater.from(parent.context),
        parent,
        false)

        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(contactsData[position], activity)
    }

    override fun getItemCount(): Int = contactsData.size

    class ViewHolder (itemView: View):
        RecyclerView.ViewHolder(itemView) {

        fun bind(contact: ContactsItem, activity: FragmentActivity?) {
            val binding = ItemContactsBinding.bind(itemView)

            with(binding) {
                contactName.text = contact.name
                contactPhoneNumber.text = contact.phoneNumber

                call.setOnClickListener {
                    checkPermission(contactPhoneNumber.text.toString(), activity)
                }
            }
        }



        private fun makePhoneCall(phoneNumber : String) {
            if (phoneNumber.isNotEmpty()){
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:$phoneNumber")
                itemView.context.startActivity(callIntent)
            }
        }

        private fun checkPermission(phoneNumber : String, activity: FragmentActivity?) {
           itemView.context?.let {
               if (activity != null) {
                   when {
                       ContextCompat.checkSelfPermission(it, Manifest.permission.CALL_PHONE)
                               == PackageManager.PERMISSION_GRANTED -> {
                           makePhoneCall(phoneNumber)
                       }

                       shouldShowRequestPermissionRationale(activity,
                           Manifest.permission.CALL_PHONE) -> {
                           showAlertMessage(activity)
                       }
                       else -> {
                           requestPermission(activity)
                       }
                   }
               }
           }
        }

        private fun showAlertMessage(activity: FragmentActivity?) {
            itemView.context.let {
                AlertDialog.Builder(it)
                    .setTitle("Доступ к звонкам")
                    .setMessage("Для осуществления звонков необходимо предоставить доступ")
                    .setPositiveButton("Предоставить доступ") { _, _ ->
                        requestPermission(activity)
                    }
                    .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
                    .create()
                    .show()
            }
        }

        private fun requestPermission(activity: FragmentActivity?) {
            activity?.let {
                requestPermissions(it,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    REQUEST_CODE)
            }
        }


    }
}