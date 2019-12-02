package com.openclassrooms.realestatemanager.controllers.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.room.RoomDatabase
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.models.RealEstate
import com.openclassrooms.realestatemanager.database.RealEstateDao
import com.openclassrooms.realestatemanager.database.AppDatabase
import com.openclassrooms.realestatemanager.models.Address
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.viewmodels.Injection
import com.openclassrooms.realestatemanager.viewmodels.RealEstateViewModel
import com.openclassrooms.realestatemanager.viewmodels.ViewModelFactory
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {


    // VIEWMODEL
    private lateinit var realEstateViewModel: RealEstateViewModel
    private lateinit var viewModelFactory: ViewModelFactory

    // SPINNERS
    private var types = arrayOf("House", "Appartment", "Building")
    private var typeSpinnerTextView: TextView? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        configureViewModel()
        configureAddButton()
        configureSpinners()
    }


    private fun configureViewModel() {

        viewModelFactory = Injection.provideViewModelFactory(this)
        realEstateViewModel = ViewModelProviders.of(this, viewModelFactory).get(RealEstateViewModel::class.java)
    }


    // Create RealEstate object from user data input
    private fun createObjectInDatabase() {


        // Create RealEstate object
        val realEstate = RealEstate()
        realEstate.address = Address()

        realEstate.description = description_text_input.text.toString()
        realEstate.type = type_spinner.selectedItemPosition
        realEstate.address?.street = address_text_input.text.toString()
        realEstate.address?.postalCode = postal_code_text_input.text.toString()
        realEstate.address?.city = city_text_input.text.toString()
        realEstate.surface = surface_text_input.text.toString().toInt()
        realEstate.price = price_text_input.text.toString().toInt()
        realEstate.nbRooms = rooms_text_input.text.toString().toInt()
        realEstate.nbBedrooms = bedrooms_text_input.text.toString().toInt()
        realEstate.nbBathrooms = bathrooms_text_input.text.toString().toInt()
        realEstate.status = false
        realEstate.creationDate = Utils.convertDate(Utils.getTodayDate().toString())

        // Save object
        realEstateViewModel.createRealEstate(realEstate)

        // Return to MainActivity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)


    }


    // Configure add button
    private fun configureAddButton() {

        add_button.setOnClickListener { createObjectInDatabase() }
    }


    // SPINNERS CONFIGURATION
    private fun configureSpinners() {

        type_spinner.onItemSelectedListener
        val typeArrayAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, types)
        type_spinner.adapter = typeArrayAdapter
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        typeSpinnerTextView!!.text = types[position]
    }


}
