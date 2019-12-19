package com.openclassrooms.realestatemanager.controllers.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.openclassrooms.realestatemanager.BuildConfig
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.controllers.fragments.DetailsFragment
import com.openclassrooms.realestatemanager.models.RealEstate
import com.openclassrooms.realestatemanager.views.DetailsPhotoAdapter
import com.openclassrooms.realestatemanager.views.ElementAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.main_toolbar.*
import java.util.*

class DetailsActivity : AppCompatActivity() {


    var realEstate: RealEstate? = RealEstate()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // Get real estate data from intent
        realEstate = intent.getSerializableExtra("REAL ESTATE") as RealEstate

        configureToolbar()

        displayFragment()


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


    private fun displayFragment() {

        val fragment = DetailsFragment(realEstate!!)

        supportFragmentManager.beginTransaction().replace(R.id.details_fragment_container, fragment).commit()
    }





}
