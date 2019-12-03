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


    // Retrieve real estate from MainActivity
    var realEstate: RealEstate? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // Get real estate data from intent
        realEstate = intent.getSerializableExtra("REAL ESTATE") as RealEstate

        setSupportActionBar(main_toolbar)
        getDataAndUpdateUI()


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.details_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // Create intent to start EditActivity
        val editIntent = Intent(this, EditActivity::class.java)
        editIntent.putExtra("EDIT REAL ESTATE", realEstate)

        when (item.itemId) {
            R.id.menu_add -> startActivity(Intent(this, AddActivity::class.java))
        }
        when (item.itemId) {
            R.id.menu_modify -> startActivity(editIntent)
        }
        return super.onOptionsItemSelected(item)
    }


    private fun getDataAndUpdateUI() {

        // Load data into views
        description_text.text = realEstate?.description
        surface_value.text = String.format(this.resources.getString(R.string.surface_in_sq), realEstate?.surface)
        rooms_value.text = realEstate?.nbRooms
        bedrooms_value.text = realEstate?.nbBedrooms
        bathrooms_value.text = realEstate?.nbBathrooms
        location_value.text = String.format(this.resources.getString(R.string.lines_address),
                realEstate?.address?.street,
                realEstate?.address?.postalCode,
                realEstate?.address?.city)


    }
}
