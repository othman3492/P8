package com.openclassrooms.realestatemanager.controllers.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.controllers.fragments.ListFragment
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.controllers.fragments.DetailsFragment
import com.openclassrooms.realestatemanager.controllers.fragments.MapFragment
import com.openclassrooms.realestatemanager.models.RealEstate
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_toolbar.*


class MainActivity : AppCompatActivity() {


    var isTablet = false
    private var realEstate = RealEstate()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_toolbar)
        updateUIWhenCreating()
        configureBottomNavigationView()


        // Verify if device is a tablet
        val detailsFragment = findViewById<View>(R.id.details_fragment_container)
        isTablet = detailsFragment?.visibility == View.VISIBLE

    }


    //----------------------------
    //USER INTERFACE
    //----------------------------


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_add -> startActivity(Intent(this, AddEditActivity::class.java))
            R.id.menu_loan -> startActivity(Intent(this, LoanActivity::class.java))
            R.id.menu_search -> startActivity(Intent(this, SearchActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }


    // Enable back button if fragment isn't home
    override fun onBackPressed() {

        val count = supportFragmentManager.backStackEntryCount

        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    private fun updateUIWhenCreating() {

        displayFragment(ListFragment.newInstance())
        displaySecondFragment(DetailsFragment.newInstance(realEstate, Bundle()))
    }


    private fun configureBottomNavigationView() {

        bottom_nav_view.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.bottom_list_view -> displayFragment(ListFragment.newInstance())
                R.id.bottom_map_view -> displayFragment(MapFragment.newInstance())
            }

            return@setOnNavigationItemSelectedListener true
        }
    }


    private fun displayFragment(fragment: Fragment) {

        val transaction = supportFragmentManager.beginTransaction()
        transaction.addToBackStack(null)
        transaction.replace(R.id.fragment_container, fragment).commit()

    }


    private fun displaySecondFragment(fragment: Fragment) {

        if (isTablet) {

            val secondTransaction = supportFragmentManager.beginTransaction()
            secondTransaction.addToBackStack(null)
            secondTransaction.replace(R.id.details_fragment_container, fragment).commit()
        }
    }







}
