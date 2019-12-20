package com.openclassrooms.realestatemanager.controllers.fragments


import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.openclassrooms.realestatemanager.BuildConfig

import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.controllers.activities.AddEditActivity
import com.openclassrooms.realestatemanager.controllers.activities.MainActivity
import com.openclassrooms.realestatemanager.models.RealEstate
import com.openclassrooms.realestatemanager.views.DetailsPhotoAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.main_toolbar.*


class DetailsFragment(var realEstate: RealEstate) : Fragment() {


    private lateinit var adapter: DetailsPhotoAdapter

    companion object {
        fun newInstance(realEstate: RealEstate, bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment(realEstate)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDataAndUpdateUI()

    }


    // Implement menu layout
    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {

        menu.clear()
        menuInflater.inflate(R.menu.details_menu, menu)
    }


    // Configure menu buttons
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // Create intent to start EditActivity
        val editIntent = Intent(activity, AddEditActivity::class.java)
        editIntent.putExtra("REAL ESTATE", realEstate)

        when (item.itemId) {
            R.id.menu_add -> startActivity(Intent(activity, AddEditActivity::class.java))
            R.id.menu_modify -> startActivity(editIntent)
        }
        return super.onOptionsItemSelected(item)
    }


    // Configure RecyclerView
    private fun configureRecyclerView() {

        adapter = DetailsPhotoAdapter(requireContext(), realEstate)
        details_photo_recycler_view.adapter = adapter
        details_photo_recycler_view.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        details_photo_recycler_view.addItemDecoration(DividerItemDecoration(
                details_photo_recycler_view.context, DividerItemDecoration.HORIZONTAL))
    }


    private fun getDataAndUpdateUI() {

        // Load photos
        configureRecyclerView()

        // Load data into views
        if (description_text.text != null) description_text.text = realEstate.description
        if (surface_value.text != null) surface_value.text = String.format(this.resources.getString(R.string.surface_in_sq), realEstate.surface)
        if (rooms_value.text != null) rooms_value.text = realEstate.nbRooms.toString()
        if (bedrooms_value.text != null) bedrooms_value.text = realEstate.nbBedrooms.toString()
        if (bathrooms_value.text != null) bathrooms_value.text = realEstate.nbBathrooms.toString()
        if (location_value.text != null) location_value.text = String.format(this.resources.getString(R.string.lines_address),
                realEstate.address?.street,
                realEstate.address?.postalCode,
                realEstate.address?.city)

        // Load static map
        Picasso.get()
                .load(loadMap(realEstate))
                .placeholder(R.drawable.baseline_map_24)
                .into(details_map)

    }


    // Display static map with place location
    private fun loadMap(realEstate: RealEstate): String {

        val location = "${realEstate.latitude},${realEstate.longitude}"

        // Set center of the map
        val mapURLInitial = "https://maps.googleapis.com/maps/api/staticmap?center=$location"
        // Set properties and marker
        val mapURLProperties = "&zoom=15&size=160x160&markers=size:tiny%7Ccolor:blue%7C$location"
        val key = "&key=${BuildConfig.static_apikey}"

        return mapURLInitial + mapURLProperties + key
    }
}



