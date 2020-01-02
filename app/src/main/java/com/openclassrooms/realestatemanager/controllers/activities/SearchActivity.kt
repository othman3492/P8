package com.openclassrooms.realestatemanager.controllers.activities

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.sqlite.db.SimpleSQLiteQuery
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.controllers.fragments.ListFragment
import com.openclassrooms.realestatemanager.models.RealEstate
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.viewmodels.Injection
import com.openclassrooms.realestatemanager.viewmodels.RealEstateViewModel
import com.openclassrooms.realestatemanager.viewmodels.ViewModelFactory
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
    private var isSpinnerVisible = true

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

        // Configure checkedTextView and show/hide spinner
        all_types_checkbox.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {

                type_search_spinner.visibility = View.INVISIBLE
                isSpinnerVisible = false
            } else {

                type_search_spinner.visibility = View.VISIBLE
                isSpinnerVisible = true
            }
        }

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

        // Verify if one or all types are selected
        var type: Int? = null
        if (isSpinnerVisible) {
            type = type_search_spinner.selectedItemPosition
        }

        val keywords = keywords_text_input.text.toString()
        val street: String? = street_search_text_input.text.toString()
        val postalCode: String? = postal_code_search_text_input.text.toString()
        val city: String? = city_search_text_input.text.toString()
        val agent: String? = agent_search_text_input.text.toString()
        val nbPhotos: Int? = nb_photos_text_input.text.toString().toIntOrNull()
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
        val minSellingDate = min_selling_date_search_field.text.toString()
        val maxSellingDate = max_selling_date_search_field.text.toString()


        var query = "SELECT * FROM properties WHERE "


        // Verify filled text inputs and add specific requests to full query

        query += if (status) {
            "status = 1 "
        } else {
            "status = 0 "
        }

        if (type != null) {
            query += "AND type = $type "
        }

        if (keywords != "") {
            query += "AND description LIKE '%$keywords%' "
        }

        if (street != "") {
            query += "AND street = '$street' "
        }

        if (postalCode != "") {
            query += "AND postalCode = '$postalCode' "
        }

        if (city != "") {
            query += "AND city = '$city' "
        }

        if (agent != "") {
            query += "AND agent = '$agent' "
        }

        if (nbPhotos != null) {
            query += "AND nbImages >= $nbPhotos "
        }

        if (minPrice != null) {
            query += "AND price >= $minPrice "
        }

        if (maxPrice != null) {
            query += "AND price <= $maxPrice "
        }

        if (minSurface != null) {
            query += "AND surface >= $minSurface "
        }

        if (maxSurface != null) {
            query += "AND surface <= $maxSurface "
        }

        if (minRooms != null) {
            query += "AND nbRooms >= $minRooms "
        }

        if (maxRooms != null) {
            query += "AND nbRooms <= $maxRooms "
        }

        if (minBedrooms != null) {
            query += "AND nbBedrooms >= $minBedrooms "
        }

        if (maxBedrooms != null) {
            query += "AND nbBedrooms <= $maxBedrooms "
        }

        if (minBathrooms != null) {
            query += "AND nbBathrooms >= $minBathrooms "
        }

        if (maxBathrooms != null) {
            query += "AND nbBathrooms <= $maxBathrooms "
        }

        if (minCreationDate != "Min. date") {
            query += "AND creationDate >= ${Utils.formatDateForQuery(minCreationDate)} "
        }

        if (maxCreationDate != "Max. date") {
            query += "AND creationDate <= ${Utils.formatDateForQuery(maxCreationDate)} "
        }

        if (minSellingDate != "Min. date") {
            query += "AND saleDate >= ${Utils.formatDateForQuery(minSellingDate)} "
        }

        if (maxSellingDate != "Max. date") {
            query += "AND saleDate <= ${Utils.formatDateForQuery(maxSellingDate)} "
        }



        // Pass query to MainActivity to display results in both ListFragment and MapFragment
        val resultsIntent = Intent(this, MainActivity::class.java)
        resultsIntent.putExtra("QUERY", query)
        startActivity(resultsIntent)

    }

}




