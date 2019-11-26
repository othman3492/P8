package com.openclassrooms.realestatemanager.controllers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Insert
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.models.RealEstate
import com.openclassrooms.realestatemanager.models.database.PropertyDao
import com.openclassrooms.realestatemanager.models.database.RoomDatabase
import com.openclassrooms.realestatemanager.utils.Utils
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

abstract class AddActivity : AppCompatActivity(), PropertyDao {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        RoomDatabase.getInstance(this, GlobalScope)
    }


    // Create RealEstate object from user data input
    private fun createObjectFromData() {

        val realEstate = RealEstate()

        realEstate.description = description_text_input.text.toString()
        realEstate.address?.street = address_text_input.text.toString()
        realEstate.address?.postalCode = postal_code_text_input.text.toString()
        realEstate.address?.city = city_text_input.text.toString()
        realEstate.surface = surface_text_input.text.toString()
        realEstate.price = price_text_input.text.toString()
        realEstate.nbRooms = rooms_text_input.text.toString()
        realEstate.nbBedrooms = bedrooms_text_input.text.toString()
        realEstate.nbBathrooms = bathrooms_text_input.text.toString()
        realEstate.status = false
        realEstate.creationDate = Utils.convertDate(Utils.getTodayDate().toString())


    }


}
