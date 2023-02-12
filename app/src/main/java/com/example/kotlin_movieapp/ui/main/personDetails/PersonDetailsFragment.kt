package com.example.kotlin_movieapp.ui.main.personDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.kotlin_movieapp.R
import com.example.kotlin_movieapp.databinding.FragmentPersonBinding
import com.example.kotlin_movieapp.model.movieDetailsResponse.Person
import com.example.kotlin_movieapp.model.personDetailsResponse.PersonDTO
import com.example.kotlin_movieapp.ui.main.DetailsState
import com.example.kotlin_movieapp.ui.main.map.MapsFragment
import com.example.kotlin_movieapp.utils.KEY_BUNDLE_PERSON
import com.example.kotlin_movieapp.utils.convert
import com.example.kotlin_movieapp.utils.showSnackBar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PersonDetailsFragment : Fragment() {

    private var _binding: FragmentPersonBinding? = null
    private val binding get() = _binding!!

    private lateinit var personBundle : Person
    private lateinit var person : PersonDTO

    private val viewModel: PersonDetailsViewModel by lazy {
        ViewModelProvider(this).get(PersonDetailsViewModel::class.java)
    }

    companion object {
        fun newInstance(bundle: Bundle) : PersonDetailsFragment {
            val fragment = PersonDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPersonBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movieDetail.visibility = View.VISIBLE
        binding.loadingLayout.visibility = View.VISIBLE

        personBundle = arguments?.getParcelable(KEY_BUNDLE_PERSON)?: Person()

        requestPersonDetail(personBundle.id)

        viewModel.getLiveData().observe(viewLifecycleOwner, Observer{
               renderData(it)
        })
    }

    private fun requestPersonDetail(personId: Int?) {
        viewModel.getPersonDetailsFromRemoteSource(personId)
    }

    private fun renderData(appState: DetailsState) {
        when (appState) {
            is DetailsState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                binding.movieDetail.showSnackBar(
                    getString(R.string.data_loading_error),
                    getString(R.string.reload),
                    {
                        viewModel.getPersonDetailsFromRemoteSource(personBundle.id)
                    },
                    0)
            }

            is DetailsState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }

            is DetailsState.SuccessPerson -> {
                binding.movieDetail.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                person = appState.personDTO
                displayPerson(appState.personDTO)
                binding.movieDetail.showSnackBar(
                    getString(R.string.data_loading_success),
                    0)
            }
            else -> return
        }
    }

    private fun displayPerson(personDTO : PersonDTO) {

        with(binding) {
            movieDetail.visibility = View.VISIBLE
            loadingLayout.visibility = View.GONE

            view?.let {
                Glide.with(it).load(personDTO.photo).into(personPhoto) }

            personName.text = personDTO.name ?: ""

            personProfession.text = personDTO.profession?.convert(){
                    profession -> profession.value}

            personAge.text = personDTO.age?.toString()

            personDateOfBirth.text = convertDate(personDTO.birthday)

            val birthLocation = personDTO.birthPlace?.convert(){
                    birthPlace ->  birthPlace.value }
            personPlaceOfBirth.text = birthLocation

            childFragmentManager
                .beginTransaction()
                .replace(R.id.placeOfBirthMapContainer, MapsFragment(birthLocation))
                .commit()
        }
    }

    private fun convertDate (date : String?) : String {
        return if (date != null) {

            val formatter = DateTimeFormatter.ISO_DATE_TIME
            val dateTime = LocalDateTime.parse(date, formatter)
            val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            dateTime.format(outputFormatter)

        } else ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}