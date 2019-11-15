package com.openclassrooms.realestatemanager.controllers.activities

import RealEstate
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.views.ElementAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: ElementAdapter
    private lateinit var elementList: ArrayList<RealEstate>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        fillList()
        configureDrawerLayout()
        configureRecyclerView()


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }


    private fun configureDrawerLayout() {

        val toggle = ActionBarDrawerToggle(this, main_drawer_layout,
                main_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        toggle.drawerArrowDrawable.color.and(R.color.white)
        main_drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun configureRecyclerView() {

        adapter = ElementAdapter(elementList)
        main_recycler_view.adapter = adapter

        linearLayoutManager = LinearLayoutManager(this)
        main_recycler_view.layoutManager = linearLayoutManager
    }


    private fun fillList() {

        val realEstate = RealEstate("Name")

        elementList = ArrayList<RealEstate>()
        elementList.add(realEstate)
        elementList.add(realEstate)
        elementList.add(realEstate)
        elementList.add(realEstate)
        elementList.add(realEstate)
        elementList.add(realEstate)

    }
}
