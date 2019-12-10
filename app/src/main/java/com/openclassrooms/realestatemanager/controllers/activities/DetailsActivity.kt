package com.openclassrooms.realestatemanager.controllers.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.openclassrooms.realestatemanager.BuildConfig
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.models.RealEstate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.main_toolbar.*
import java.util.*

class DetailsActivity : AppCompatActivity() {


    // Retrieve real estate from MainActivity
    var realEstate: RealEstate? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // Get real estate data from intent
        realEstate = intent.getSerializableExtra("REAL ESTATE") as RealEstate

        configureToolbar()
        getDataAndUpdateUI()


    }


    // Set toolbar and display back to home button
    private fun configureToolbar() {

        setSupportActionBar(main_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        main_toolbar.setNavigationOnClickListener { onBackPressed() }
    }


    // Configure back to home button
    override fun onBackPressed() {

        startActivity(Intent(this, MainActivity::class.java))
        super.onBackPressed()
    }


    // Implement menu layout
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.details_menu, menu)
        return true
    }


    // Configure menu buttons
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // Create intent to start EditActivity
        val editIntent = Intent(this, AddEditActivity::class.java)
        editIntent.putExtra("REAL ESTATE", realEstate)

        when (item.itemId) {
            R.id.menu_add -> startActivity(Intent(this, AddEditActivity::class.java))
            R.id.menu_modify -> startActivity(editIntent)
        }
        return super.onOptionsItemSelected(item)
    }


    private fun getDataAndUpdateUI() {

        // Load data into views
        description_text.text = realEstate?.description
        surface_value.text = String.format(this.resources.getString(R.string.surface_in_sq), realEstate?.surface)
        rooms_value.text = realEstate?.nbRooms.toString()
        bedrooms_value.text = realEstate?.nbBedrooms.toString()
        bathrooms_value.text = realEstate?.nbBathrooms.toString()
        location_value.text = String.format(this.resources.getString(R.string.lines_address),
                realEstate?.address?.street,
                realEstate?.address?.postalCode,
                realEstate?.address?.city)

        // Load static map
        Picasso.get()
                .load(loadMap(realEstate!!))
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
