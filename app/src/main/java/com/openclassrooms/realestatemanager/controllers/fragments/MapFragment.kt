package com.openclassrooms.realestatemanager.controllers.fragments


import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener

import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.models.RealEstate
import com.openclassrooms.realestatemanager.viewmodels.Injection
import com.openclassrooms.realestatemanager.viewmodels.RealEstateViewModel
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.fragment_map.*


class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    private lateinit var realEstateViewModel: RealEstateViewModel

    companion object {
        private const val LOCATION_REQUEST_CODE = 1

        fun newInstance() = MapFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (map_view != null) {
            map_view.onCreate(null)
            map_view.onResume()
            map_view.getMapAsync(this)
        }
    }

    override fun onMapReady(map: GoogleMap) {

        googleMap = map

        getLocationPermission()
        getDeviceLocation(map)
        configureViewModel()
        getData()
    }


    // Verify if user's location permission is granted, and request it if not
    private fun getLocationPermission() {

        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
            return
        }
    }


    // Enable pointer on user's location, get their most recent location available, and position the map's camera
    private fun getDeviceLocation(map: GoogleMap) {

        map.isMyLocationEnabled = true

        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location ->

            lastLocation = location
            val currentLatLng = LatLng(location.latitude, location.longitude)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
        }


    }


    // Configure ViewModel to retrieve data
    private fun configureViewModel() {

        val viewModelFactory = Injection.provideViewModelFactory(requireContext())
        realEstateViewModel = ViewModelProviders.of(this,
                viewModelFactory).get(RealEstateViewModel::class.java)
    }


    // Retrieve real estate data and pass it to list, then call SetMarkersOnMap
    private fun getData() {

        realEstateViewModel.getAllRealEstates().observe(this,
                Observer<List<RealEstate>> { setMarkersOnMap(googleMap, it) })
    }


    // Create markers on map for each retrieved Real Estate
    private fun setMarkersOnMap(map: GoogleMap, list: List<RealEstate>) {

        for (realEstate in list) {

            map.addMarker(MarkerOptions()
                    .position(LatLng(requireNotNull(realEstate.longitude),
                            requireNotNull(realEstate.latitude))))
        }
    }


    override fun onMarkerClick(p0: Marker?) = false
}
