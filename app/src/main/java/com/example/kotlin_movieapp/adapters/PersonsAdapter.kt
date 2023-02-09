package com.example.kotlin_movieapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_movieapp.databinding.ItemPersonsBinding
import com.example.kotlin_movieapp.model.movieDetailsResponse.Person
import com.example.kotlin_movieapp.ui.main.personDetails.PersonDetailsFragment
import com.example.kotlin_movieapp.utils.KEY_BUNDLE_PERSON
import com.example.kotlin_movieapp.utils.openDetailsFragment
import com.squareup.picasso.Picasso

class PersonsAdapter(
    private var personsData: List<Person>
    ) : RecyclerView.Adapter<PersonsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPersonsBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)

        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(personsData[position])
    }

    override fun getItemCount(): Int = personsData.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(person: Person) {
            val binding = ItemPersonsBinding.bind(itemView)
            with(binding) {

                Picasso.get()?.load(person.photo)?.into(personPoster)

                personName.text = person.name

                root.setOnClickListener {
                    it.openDetailsFragment(
                        PersonDetailsFragment::class.java,
                        KEY_BUNDLE_PERSON,
                        person
                    )
                }
            }
        }
    }
}