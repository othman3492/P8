package com.openclassrooms.realestatemanager.controllers.activities

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R
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

    var calendar: Calendar = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        configureUI()


    }


    private fun configureUI() {

        // Configure spinner
        configureSpinner()

        // Configure date pickers
        configureDatePicker(min_creation_date_search_text_input)
        configureDatePicker(max_creation_date_search_text_input)
        configureDatePicker(min_selling_date_search_text_input)
        configureDatePicker(min_selling_date_search_text_input)

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


    private fun configureDatePicker(editText: EditText) {

        calendar = Calendar.getInstance()

        // Create an OnDateSetListener
        val dateSetListener = OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            updateDateInView()
        }

        // Open DatePickerDialog when clicked
        editText.setOnClickListener {
            DatePickerDialog(this@SearchActivity, dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }


    // Create pattern and display selected date
    private fun updateDateInView() {

        val myFormat = "dd/MM/yy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        min_creation_date_search_text_input!!.setText(sdf.format(calendar.time))
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
}


