package com.example.kotlin_movieapp.ui.main.map

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.kotlin_movieapp.R
import com.example.kotlin_movieapp.databinding.FragmentBirthPlaceMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapsFragment (
    private var location : String?
        ) : Fragment() {

    private var _binding: FragmentBirthPlaceMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var map: GoogleMap
    private val defaultCity = LatLng(55.0, 37.0)
    private val defaultCityName = "Москва"
    private var fragmentContext: Context? = null

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        map.uiSettings.isZoomControlsEnabled = true

        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val marker = checkLocation(geocoder, location)
        if (fragmentContext != null) {
            if (marker == defaultCity) {
                Toast.makeText(context,
                    getString(R.string.notFoundBirthCityGeoposition),
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }
        map.addMarker(MarkerOptions().position(marker).title(location))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 5f))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBirthPlaceMapBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun checkLocation (geocoder: Geocoder, location : String?): LatLng {
        return if (location == "") {
            defaultCity
        } else {
            val searchResult = checkGeoResult(geocoder, geocoder.getFromLocationName(location, 1))
            val lat = searchResult[0].latitude
            val long= searchResult[0].longitude
            LatLng(lat, long)
        }
    }

    private fun checkGeoResult (geocoder: Geocoder, searchResult: List<Address>): List<Address> {
        return searchResult ?: geocoder.getFromLocationName(defaultCityName, 1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        fragmentContext = null
    }
}