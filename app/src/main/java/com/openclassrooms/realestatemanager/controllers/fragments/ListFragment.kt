package com.openclassrooms.realestatemanager.controllers.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.models.RealEstate
import com.openclassrooms.realestatemanager.views.ElementAdapter
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: ElementAdapter
    private lateinit var elementList: ArrayList<RealEstate>

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

        fillList()
        configureRecyclerView()
    }

    private fun configureRecyclerView() {

        adapter = ElementAdapter(elementList)
        main_recycler_view.adapter = adapter
        linearLayoutManager = LinearLayoutManager(activity)
        main_recycler_view.layoutManager = linearLayoutManager
        main_recycler_view.addItemDecoration(DividerItemDecoration(main_recycler_view.context, DividerItemDecoration.VERTICAL))
    }


    private fun fillList() {

        val realEstate = RealEstate("Name", 2000000)

        elementList = ArrayList()
        elementList.add(realEstate)
        elementList.add(realEstate)
        elementList.add(realEstate)
        elementList.add(realEstate)
        elementList.add(realEstate)
        elementList.add(realEstate)

    }


}



