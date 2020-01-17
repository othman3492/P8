package com.openclassrooms.realestatemanager.controllers.activities

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
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
        agent_text_input.setText(realEstate.agent)
        if (realEstate.surface != null) surface_text_input.setText(realEstate.surface.toString())
        if (realEstate.price != null) price_text_input.setText(realEstate.price.toString())
        if (realEstate.nbRooms != null) rooms_text_input.setText(realEstate.nbRooms.toString())
        if (realEstate.nbBedrooms != null) bedrooms_text_input.setText(realEstate.nbBedrooms.toString())
        if (realEstate.nbBathrooms != null) bathrooms_text_input.setText(realEstate.nbBathrooms.toString())

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
    private fun createObjectInDatabase(newRealEstate: RealEstate) {

        getDataFromInput(newRealEstate)

        if (newRealEstate.address?.street != "" && newRealEstate.address?.postalCode != "" &&
                newRealEstate.address?.city != "") {

            if (Utils.isInternetAvailable(this)) {

                newRealEstate.latitude = getLocationFromAddress(realEstate.address.toString())?.latitude
                newRealEstate.longitude = getLocationFromAddress(realEstate.address.toString())?.longitude
            }

            // Save object
            realEstateViewModel.createRealEstate(newRealEstate)

            // Confirm creation
            Toast.makeText(this, "New real estate created !", Toast.LENGTH_SHORT).show()

            // Return to MainActivity
            startActivity(Intent(this, MainActivity::class.java))

        } else {

            // Display Toast message if address is null
            Toast.makeText(this, "Please enter an address", Toast.LENGTH_SHORT).show()
        }
    }

    // Update RealEstate object from user data input
    private fun updateObjectInDatabase(realEstate: RealEstate) {

        getDataFromInput(realEstate)

        if (realEstate.address?.street != "" && realEstate.address?.postalCode != "" &&
                realEstate.address?.city != "") {

            if (Utils.isInternetAvailable(this)) {

                realEstate.latitude = getLocationFromAddress(realEstate.address.toString())?.latitude
                realEstate.longitude = getLocationFromAddress(realEstate.address.toString())?.longitude
            }

            // Update object
            realEstateViewModel.updateRealEstate(realEstate)

            // Return to DetailsFragment
            finish()

        } else {

            // Display Toast message if address is null
            Toast.makeText(this, "Please enter an address", Toast.LENGTH_SHORT).show()
        }
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


    // Get latitude/longitude from address input
    private fun getLocationFromAddress(strAddress: String?): LatLng? {

        val coder = Geocoder(this)
        val address: List<android.location.Address>?
        val latlng: LatLng?

        address = coder.getFromLocationName(strAddress, 3)
        return if (address.isEmpty()) {

            null
        } else {

            val location = address[0]
            latlng = LatLng(location.latitude, location.longitude)

            latlng
        }
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
        add_button.setOnClickListener { createObjectInDatabase(realEstate) }
        edit_button.setOnClickListener { updateObjectInDatabase(realEstate) }
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
