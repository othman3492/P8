package com.openclassrooms.realestatemanager.controllers.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.models.RealEstate
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.main_toolbar.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setSupportActionBar(main_toolbar)
        getDataAndUpdateUI()

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.details_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_add -> startActivity(Intent(this, AddActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }


    private fun getDataAndUpdateUI() {

        // Retrieve real estate from intent
        val realEstate = intent.getSerializableExtra("REAL ESTATE") as RealEstate

        // Load data into views
        description_text.text = realEstate.description
        surface_value.text = String.format(R.string.surface_in_sq.toString(), realEstate.surface)
        rooms_value.text = realEstate.nbRooms.toString()
        bedrooms_value.text = realEstate.nbBedrooms.toString()
        bathrooms_value.text = realEstate.nbBathrooms.toString()
        location_value.text = realEstate.address.toString()


    }
}
