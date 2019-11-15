package com.openclassrooms.realestatemanager.views

import RealEstate
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import kotlinx.android.synthetic.main.list_element_layout.view.*


class ElementAdapter(private val elements: ArrayList<RealEstate>) : RecyclerView.Adapter<ElementAdapter.ElementViewHolder>() {


    override fun getItemCount() = elements.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewHolder {

        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.list_element_layout, parent, false)
        return ElementViewHolder(v)
    }


    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {

        holder.populateViewHolder(elements[position])
    }


    class ElementViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {


        private var view: View = v
        private var realEstate: RealEstate? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Log.d("TAG", "Click")
        }

        companion object {

            private val POSITION = "POSITION"
        }


        fun populateViewHolder(realEstate: RealEstate) {

            view.element_type.text = "Type"
            view.element_location.text = "Location"
            view.element_price.text = "10 000 000 $"
        }
    }


}


