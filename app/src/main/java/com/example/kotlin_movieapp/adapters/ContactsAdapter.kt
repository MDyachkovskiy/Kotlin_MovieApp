package com.example.kotlin_movieapp.adapters

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_movieapp.databinding.ItemContactsBinding
import com.example.kotlin_movieapp.model.room.contacts.ContactsItem
import com.example.kotlin_movieapp.utils.checkPermission

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
                    if (checkPermission(Manifest.permission.CALL_PHONE, activity, itemView)) {
                        makePhoneCall(contactPhoneNumber.text.toString())
                    }
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
    }
}