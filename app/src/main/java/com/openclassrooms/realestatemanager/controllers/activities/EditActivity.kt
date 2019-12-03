package com.openclassrooms.realestatemanager.controllers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.models.RealEstate
import kotlinx.android.synthetic.main.activity_add_edit.*

class EditActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    var realEstate: RealEstate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        // Get real estate data from intent
        realEstate = intent.getSerializableExtra("EDIT REAL ESTATE") as RealEstate


        configureUI()
    }


    // Show/hide different views from AddActivity, and fill input text with data from current real estate
    private fun configureUI() {

        // Set views' visibility
        add_property.visibility = View.GONE
        add_button.visibility = View.GONE
        edit_property.visibility = View.VISIBLE
        edit_button.visibility = View.VISIBLE

        // Fill input texts
        description_text_input.setText(realEstate?.description)
        street_text_input.setText(realEstate?.address?.street)
        postal_code_text_input.setText(realEstate?.address?.postalCode)
        city_text_input.setText(realEstate?.address?.city)
        surface_text_input.setText(realEstate?.surface)
        price_text_input.setText(realEstate?.price)
        agent_text_input.setText(realEstate?.agent)
        rooms_text_input.setText(realEstate?.nbRooms)
        bedrooms_text_input.setText(realEstate?.nbBedrooms)
        bathrooms_text_input.setText(realEstate?.nbBathrooms)

        // Update other views
        if (realEstate?.status == true) {
            status_switch.isChecked
        }


    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
