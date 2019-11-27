package com.openclassrooms.realestatemanager.controllers.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.controllers.activities.DetailsActivity
import com.openclassrooms.realestatemanager.models.RealEstate
import com.openclassrooms.realestatemanager.database.AppDatabase
import com.openclassrooms.realestatemanager.views.ElementAdapter
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: ElementAdapter
    private lateinit var elementList: List<RealEstate>

    private lateinit var db: AppDatabase

    companion object {
        fun newInstance(): ListFragment {
            return ListFragment()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecyclerView()
    }


    // Configure RecyclerView and assign the click handler to the Adapter
    private fun configureRecyclerView() {

        adapter = ElementAdapter(elementList) { realEstate: RealEstate -> setElementOnClick(realEstate) }
        main_recycler_view.adapter = adapter
        linearLayoutManager = LinearLayoutManager(activity)
        main_recycler_view.layoutManager = linearLayoutManager
        main_recycler_view.addItemDecoration(DividerItemDecoration(main_recycler_view.context, DividerItemDecoration.VERTICAL))
    }



    private fun setElementOnClick(realEstate: RealEstate) {

        startActivity(Intent(context, DetailsActivity::class.java))
    }


}



