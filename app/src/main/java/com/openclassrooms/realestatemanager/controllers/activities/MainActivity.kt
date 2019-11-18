package com.openclassrooms.realestatemanager.controllers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.controllers.fragments.MainFragment
import com.openclassrooms.realestatemanager.models.RealEstate
import com.openclassrooms.realestatemanager.views.ElementAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragmentActivity() {

    private val fragmentManager = supportFragmentManager
    private val mainFragment = MainFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        updateUIWhenCreating()
        configureDrawerLayout()


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }


    private fun configureDrawerLayout() {

        val toggle = ActionBarDrawerToggle(this, main_drawer_layout,
                main_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        main_drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun updateUIWhenCreating() {

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.attach(mainFragment).commit()
    }

}
