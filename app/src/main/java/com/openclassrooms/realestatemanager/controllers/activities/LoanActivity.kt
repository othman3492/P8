package com.openclassrooms.realestatemanager.controllers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.openclassrooms.realestatemanager.R
import kotlinx.android.synthetic.main.activity_loan.*
import java.math.RoundingMode
import kotlin.math.pow
import java.math.BigDecimal as BigDecimal1

class LoanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan)

        setCalculateButton()

    }


    // Calculate loan from data input
    private fun calculateLoan() {

        var contribution = 0.0

        if (capital_contribution_text_input.text != null) {
            contribution = capital_contribution_text_input.text.toString().toDouble()
        }

        val loan = loan_amount_text_input.text.toString().toDouble()
        val interest = interest_rate_text_input.text.toString().toDouble()
        val length = loan_period_text_input.text.toString().toInt()

        val monthlyPayment = ((loan - contribution) * (interest / 100) / 12) / (1 - (1 + (interest / 100) / 12).pow(-(length * 12)))
        val totalPayment = monthlyPayment * length * 12
        val interests = totalPayment - loan - contribution

        total_payment_value.text = String.format("%.2f", totalPayment)
        monthly_payment_value.text = String.format("%.2f", monthlyPayment)
        interests_amount_value.text = String.format("%.2f", interests)

    }


    private fun setCalculateButton() {

        calculate_button.setOnClickListener { calculateLoan() }


    }
}
