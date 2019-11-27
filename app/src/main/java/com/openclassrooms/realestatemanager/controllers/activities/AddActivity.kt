package com.openclassrooms.realestatemanager.controllers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.RoomDatabase
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.models.RealEstate
import com.openclassrooms.realestatemanager.database.RealEstateDao
import com.openclassrooms.realestatemanager.database.AppDatabase
import com.openclassrooms.realestatemanager.utils.Utils
import kotlinx.android.synthetic.main.activity_add.*

abstract class AddActivity : AppCompatActivity(), RealEstateDao {

    private lateinit var db: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        configureAddButton()
    }


    // Create RealEstate object from user data input
    private fun createObjectInDatabase() {

        val realEstate = RealEstate()

        realEstate.description = description_text_input.text.toString()
        realEstate.address?.street = address_text_input.text.toString()
        realEstate.address?.postalCode = postal_code_text_input.text.toString()
        realEstate.address?.city = city_text_input.text.toString()
        realEstate.surface = surface_text_input.text.toString()
        realEstate.price = price_text_input.text.toString() + " $"
        realEstate.nbRooms = rooms_text_input.text.toString()
        realEstate.nbBedrooms = bedrooms_text_input.text.toString()
        realEstate.nbBathrooms = bathrooms_text_input.text.toString()
        realEstate.status = false
        realEstate.creationDate = Utils.convertDate(Utils.getTodayDate().toString())

        db.realEstateDao().createRealEstate(realEstate)


    }


    private fun configureAddButton() {

        add_button.setOnClickListener { createObjectInDatabase() }
    }


}
