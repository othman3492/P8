package com.openclassrooms.realestatemanager.controllers.fragments


import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.models.RealEstate
import com.openclassrooms.realestatemanager.viewmodels.Injection
import com.openclassrooms.realestatemanager.viewmodels.RealEstateViewModel
import kotlinx.android.synthetic.main.fragment_map.*


class MapFragment : Fragment(), OnMapReadyCallback {

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

        getLocationPermission()

        if (map_view != null) {
            map_view.onCreate(null)
            map_view.onResume()
            map_view.getMapAsync(this)
        }
    }

    override fun onMapReady(map: GoogleMap) {

        googleMap = map

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


    private fun configureViewModel() {

        val viewModelFactory = Injection.provideViewModelFactory(requireContext())
        realEstateViewModel = ViewModelProviders.of(this,
                viewModelFactory).get(RealEstateViewModel::class.java)
    }


    // Retrieve all objects from database and call setMarkersOnMap
    private fun getData() {

        realEstateViewModel.getAllRealEstates().observe(this,
                Observer<List<RealEstate>> { setMarkersOnMap(googleMap, it) })
    }


    // Create markers on map for each object found in database
    private fun setMarkersOnMap(map: GoogleMap, list: List<RealEstate>) {

        for (realEstate in list) {

            if (realEstate.latitude != null && realEstate.longitude != null) {


                val location = LatLng(realEstate.latitude!!, realEstate.longitude!!)

                val marker = map.addMarker(MarkerOptions()
                        .position(location))

                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))

                map.setOnMarkerClickListener {

                    for (realEstate1 in list) {

                        val location1 = LatLng(realEstate1.latitude!!, realEstate1.longitude!!)

                        if (location1 == it.position) {

                            // Display DetailsFragment when clicked after verifying if device is a tablet
                            val isTablet = resources.getBoolean(R.bool.isTablet)
                            val fragment = DetailsFragment.newInstance(realEstate1)

                            val transaction = activity!!.supportFragmentManager.beginTransaction()
                            transaction.addToBackStack(null)

                            if (isTablet) {
                                transaction.replace(R.id.details_fragment_container, fragment).commit()
                            } else {
                                transaction.replace(R.id.fragment_container, fragment).commit()
                            }

                        }

                    }

                    return@setOnMarkerClickListener true
                }
            }
        }
    }
}



