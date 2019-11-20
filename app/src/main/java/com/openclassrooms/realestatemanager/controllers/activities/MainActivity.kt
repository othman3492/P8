package com.openclassrooms.realestatemanager.controllers.activities

import android.content.Context
import android.os.Bundle
import android.view.Menu
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.controllers.fragments.ListFragment
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.controllers.fragments.MapFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_toolbar.*


class MainActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_toolbar)
        updateUIWhenCreating()
        configureBottomNavigationView()


    }


    //----------------------------
    //USER INTERFACE
    //----------------------------

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }


    private fun updateUIWhenCreating() {

        displayFragment(ListFragment.newInstance())
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

        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }

}
