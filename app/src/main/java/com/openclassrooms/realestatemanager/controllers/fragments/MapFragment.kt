package com.openclassrooms.realestatemanager.controllers.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import com.openclassrooms.realestatemanager.R
import kotlinx.android.synthetic.main.fragment_map.*


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap

    companion object {
        fun newInstance() = MapFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onMapReady(map: GoogleMap) {

        googleMap = map

        map.addMarker(MarkerOptions().position(LatLng(-34.0, 151.0)).title("test"))
        map.moveCamera(CameraUpdateFactory.newLatLng(LatLng(-34.0, 151.0)))

    }


}
