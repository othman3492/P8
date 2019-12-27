package com.openclassrooms.realestatemanager.controllers.activities

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Build
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.models.RealEstate
import com.openclassrooms.realestatemanager.viewmodels.Injection
import com.openclassrooms.realestatemanager.viewmodels.RealEstateViewModel
import com.openclassrooms.realestatemanager.viewmodels.ViewModelFactory
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_search.*
import java.text.SimpleDateFormat
import java.util.*


class SearchActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {


    // VIEWMODEL
    private lateinit var realEstateViewModel: RealEstateViewModel
    private lateinit var viewModelFactory: ViewModelFactory

    // SPINNERS
    private var types: Array<String>? = null
    private var typeSpinnerTextView: TextView? = null

    var calendar: Calendar = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        configureUI()
        configureViewModel()


    }


    private fun configureUI() {

        // Configure spinner
        configureSpinner()

        // Configure search button
        configureButtons()

        // Configure date pickers
        configureDatePicker(min_creation_date_search_field)
        configureDatePicker(max_creation_date_search_field)
        configureDatePicker(min_selling_date_search_field)
        configureDatePicker(max_selling_date_search_field)

        // Show selling date pickers if sold switch is checked
        status_search_switch.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {

                search_selling_date_textview.visibility = View.VISIBLE
                min_selling_date_search_field.visibility = View.VISIBLE
                max_selling_date_search_field.visibility = View.VISIBLE

            } else {

                search_selling_date_textview.visibility = View.GONE
                min_selling_date_search_field.visibility = View.GONE
                max_selling_date_search_field.visibility = View.GONE
            }
        }
    }


    private fun configureViewModel() {

        viewModelFactory = Injection.provideViewModelFactory(this)
        realEstateViewModel = ViewModelProviders.of(this, viewModelFactory).get(RealEstateViewModel::class.java)
    }

    private fun configureButtons() {

        search_property_button.setOnClickListener { executeUserSearch() }
    }


    private fun configureDatePicker(textView: TextView) {

        calendar = Calendar.getInstance()

        // Create an OnDateSetListener
        val dateSetListener = OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            updateDateInView(textView)
        }

        // Open DatePickerDialog when clicked
        textView.setOnClickListener {
            DatePickerDialog(this@SearchActivity, dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }


    // Create pattern and display selected date
    private fun updateDateInView(textView: TextView) {

        val myFormat = "dd/MM/yy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textView.setText(sdf.format(calendar.time))
    }


    // SPINNER CONFIGURATION
    private fun configureSpinner() {

        types = this.resources.getStringArray(R.array.types_array)

        type_search_spinner.onItemSelectedListener
        val typeArrayAdapter = ArrayAdapter(this, R.layout.spinner_parameters, types as Array<out String>)
        type_search_spinner.adapter = typeArrayAdapter
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        typeSpinnerTextView!!.text = types!![position]
    }


    private fun executeUserSearch() {

        val keywords = keywords_text_input.text.toString().split(" ")
        val street: String? = street_search_text_input.text.toString()
        val postalCode: String? = postal_code_search_text_input.text.toString()
        val city: String? = city_search_text_input.text.toString()
        val agent: String? = agent_search_text_input.text.toString()
        val type: Int = type_search_spinner.selectedItemPosition
        val minPrice: Int? = min_price_search_text_input.text.toString().toIntOrNull()
        val maxPrice: Int? = max_price_search_text_input.text.toString().toIntOrNull()
        val minSurface: Int? = min_surface_search_text_input.text.toString().toIntOrNull()
        val maxSurface: Int? = max_surface_search_text_input.text.toString().toIntOrNull()
        val minRooms: Int? = min_rooms_search_text_input.text.toString().toIntOrNull()
        val maxRooms: Int? = max_rooms_search_text_input.text.toString().toIntOrNull()
        val minBedrooms: Int? = min_bedrooms_search_text_input.text.toString().toIntOrNull()
        val maxBedrooms: Int? = max_bedrooms_search_text_input.text.toString().toIntOrNull()
        val minBathrooms: Int? = min_bathrooms_search_text_input.text.toString().toIntOrNull()
        val maxBathrooms: Int? = max_bathrooms_search_text_input.text.toString().toIntOrNull()
        val minCreationDate = min_creation_date_search_field.text.toString()
        val maxCreationDate = max_creation_date_search_field.text.toString()
        val status = status_search_switch.isChecked


        realEstateViewModel.getRealEstateFromUserSearch(street, postalCode, city,
                agent, type, minPrice, maxPrice, minSurface, maxSurface, minRooms, maxRooms, minBedrooms, maxBedrooms, minBathrooms,
                maxBathrooms, status).observe(this,
                Observer<List<RealEstate>> { Toast.makeText(this, it[0].address!!.city, Toast.LENGTH_SHORT).show() })
    }
}


