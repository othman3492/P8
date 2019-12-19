package com.openclassrooms.realestatemanager.controllers.activities

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.controllers.fragments.PhotoFragment
import com.openclassrooms.realestatemanager.models.Address
import com.openclassrooms.realestatemanager.models.RealEstate
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.viewmodels.Injection
import com.openclassrooms.realestatemanager.viewmodels.RealEstateViewModel
import com.openclassrooms.realestatemanager.viewmodels.ViewModelFactory
import kotlinx.android.synthetic.main.activity_add_edit.*
import kotlinx.android.synthetic.main.activity_add_edit.city_search_text_input
import kotlinx.android.synthetic.main.activity_add_edit.keywords_text_input
import kotlinx.android.synthetic.main.activity_add_edit.postal_code_search_text_input
import kotlinx.android.synthetic.main.activity_add_edit.search_property
import kotlinx.android.synthetic.main.activity_add_edit.street_search_text_input
import kotlinx.android.synthetic.main.activity_search.*
import java.io.IOException


class AddEditActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, PhotoFragment.OnDismissListener {


    // VIEWMODEL
    private lateinit var realEstateViewModel: RealEstateViewModel
    private lateinit var viewModelFactory: ViewModelFactory

    // SPINNERS
    private var types: Array<String>? = null
    private var typeSpinnerTextView: TextView? = null

    private var realEstate: RealEstate = RealEstate()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        init()
    }


    private fun init() {

        configureSpinner()
        configureViewModel()
        configureButtons()

        // Get data from existing real estate and display it
        if (intent.getSerializableExtra("REAL ESTATE") != null) {

            realEstate = intent.getSerializableExtra("REAL ESTATE") as RealEstate

            fillData()
        }
    }


    // Show/hide views depending on Add/Edit layout
    private fun fillData() {

        // Set views' visibility
        search_property.visibility = View.GONE
        add_button.visibility = View.GONE
        edit_property.visibility = View.VISIBLE
        edit_button.visibility = View.VISIBLE

        // Fill input texts
        keywords_text_input.setText(realEstate.description)
        street_search_text_input.setText(realEstate.address?.street)
        postal_code_search_text_input.setText(realEstate.address?.postalCode)
        city_search_text_input.setText(realEstate.address?.city)
        surface_text_input.setText(realEstate.surface.toString())
        price_text_input.setText(realEstate.price.toString())
        agent_text_input.setText(realEstate.agent.toString())
        rooms_text_input.setText(realEstate.nbRooms.toString())
        bedrooms_text_input.setText(realEstate.nbBedrooms.toString())
        bathrooms_text_input.setText(realEstate.nbBathrooms.toString())

        // Update other views
        if (realEstate.status == true) {
            status_switch.isChecked = true
        }

        type_spinner.setSelection(realEstate.type!!)

    }


    private fun configureViewModel() {

        viewModelFactory = Injection.provideViewModelFactory(this)
        realEstateViewModel = ViewModelProviders.of(this, viewModelFactory).get(RealEstateViewModel::class.java)
    }


    // Create RealEstate object from user data input
    private fun createObjectInDatabase() {

        val newRealEstate = RealEstate()
        getDataFromInput(newRealEstate)

        // Save object
        realEstateViewModel.createRealEstate(newRealEstate)

        // Return to MainActivity
        startActivity(Intent(this, MainActivity::class.java))

    }

    // Create RealEstate object from user data input
    private fun updateObjectInDatabase() {

        getDataFromInput(realEstate)

        // Update object
        realEstateViewModel.updateRealEstate(realEstate)

        // Return to DetailsActivity
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("REAL ESTATE", realEstate)
        startActivity(intent)

    }


    // Apply data to RealEstate object
    private fun getDataFromInput(realEstate: RealEstate) {

        realEstate.address = Address()

        realEstate.description = keywords_text_input.text.toString()
        realEstate.type = type_spinner.selectedItemPosition
        realEstate.address?.street = street_search_text_input.text.toString()
        realEstate.address?.postalCode = postal_code_search_text_input.text.toString()
        realEstate.address?.city = city_search_text_input.text.toString()
        realEstate.surface = surface_text_input.text.toString().toIntOrNull()
        realEstate.price = price_text_input.text.toString().toIntOrNull()
        realEstate.nbRooms = rooms_text_input.text.toString().toIntOrNull()
        realEstate.nbBedrooms = bedrooms_text_input.text.toString().toIntOrNull()
        realEstate.nbBathrooms = bathrooms_text_input.text.toString().toIntOrNull()
        realEstate.creationDate = Utils.convertDate(Utils.getTodayDate().toString())
        realEstate.agent = agent_text_input.text.toString()
        realEstate.latitude = getLocationFromAddress(baseContext, realEstate.address.toString())!!.latitude
        realEstate.longitude = getLocationFromAddress(baseContext, realEstate.address.toString())!!.longitude
        realEstate.status = status_switch.isChecked


        // Save today date as sale date if switch is checked and no sale date is already saved
        if (status_switch.isChecked && realEstate.saleDate == null) {

            realEstate.saleDate = Utils.convertDate(Utils.getTodayDate().toString())
        } else if (!status_switch.isChecked) {

            realEstate.saleDate = null
        }

        // Save image list
        realEstate.imageList = this.realEstate.imageList
        realEstate.nbImages = realEstate.imageList.size
    }


    // Get latitude/longitude from address string
    private fun getLocationFromAddress(context: Context, strAddress: String): LatLng? {

        val coder = Geocoder(context)
        val address: List<android.location.Address>?
        var latlng: LatLng? = null

        try {
            address = coder.getFromLocationName(strAddress, 3)
            if (address == null) {
                return null
            }
            val location = address[0]
            latlng = LatLng(location.latitude, location.longitude)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }

        return latlng
    }


    // SPINNER CONFIGURATION
    private fun configureSpinner() {

        types = this.resources.getStringArray(R.array.types_array)

        type_spinner.onItemSelectedListener
        val typeArrayAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, types as Array<out String>)
        type_spinner.adapter = typeArrayAdapter
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        typeSpinnerTextView!!.text = types!![position]
    }


    // Configure add/edit and photo buttons
    private fun configureButtons() {

        manage_photos_button.setOnClickListener { displayDialogFragment() }
        add_button.setOnClickListener { createObjectInDatabase() }
        edit_button.setOnClickListener { updateObjectInDatabase() }
    }


    // Create dialog fragment and pass it arguments from created bundle if real estate is non-null
    private fun displayDialogFragment() {


        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val prev = supportFragmentManager.findFragmentByTag("dialog")
        if (prev != null) {
            fragmentTransaction.remove(prev)
        }
        fragmentTransaction.addToBackStack(null)

        val dialogFragment = PhotoFragment()

        // Pass data from real estate to display photos
        val bundle = Bundle()
        bundle.putSerializable("PHOTO_REAL_ESTATE", realEstate)
        dialogFragment.arguments = bundle

        dialogFragment.show(fragmentTransaction, "dialog")
        dialogFragment.setOnDismissListener(this)
    }


    // Update real estate image list when dialog is dismissed
    override fun dismissed(realEstate: RealEstate) {

        this.realEstate.imageList = realEstate.imageList
    }


}
