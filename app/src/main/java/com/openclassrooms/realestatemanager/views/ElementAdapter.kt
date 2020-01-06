package com.openclassrooms.realestatemanager.views

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.models.RealEstate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_element_layout.view.*


// Create Adapter with a click listener (parameter RealEstate, return nothing)
class ElementAdapter(val context: Context, private val clickListener: (RealEstate) -> Unit) :
        RecyclerView.Adapter<ElementAdapter.ElementViewHolder>() {


    private var elements: List<RealEstate> = ArrayList()


    override fun getItemCount() = elements.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewHolder {

        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.list_element_layout, parent, false)
        return ElementViewHolder(v, parent.context)
    }

    // Populate ViewHolder with data depending on the position in the list, and configure the click
    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {

        holder.bind(elements[position], clickListener)
    }


    fun updateData(list: List<RealEstate>) {

        this.elements = list
        this.notifyDataSetChanged()
    }


    class ElementViewHolder(v: View, private var context: Context) : RecyclerView.ViewHolder(v), View.OnClickListener {


        private var view: View = v
        private var types = arrayOf("House", "Appartment", "Building")


        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Log.d("TAG", "Click")
        }


        // Assign data to the views and handle click events through a function parameter
        fun bind(realEstate: RealEstate, clickListener: (RealEstate) -> Unit) {

            if (realEstate.imageList.size > 0) {

                Picasso.get().load(Uri.parse(realEstate.imageList[0])).into(view.element_image)
            } else {

                view.element_image.setBackgroundResource(R.drawable.baseline_black_house_24)
            }

            view.element_type.text = types[requireNotNull(realEstate.type)]
            view.element_location.text = realEstate.address?.city

            // Display "-" instead of price if null
            if (realEstate.price != null) {
                view.element_price.text = String.format(context.resources.getString(R.string.price_in_dollars), realEstate.price)
            } else {
                view.element_price.text = String.format(context.resources.getString(R.string.price_in_dollars), " - ")
            }

            // Display red SOLD sign if real estate is sold
            if (realEstate.status!!) {
                view.sold_textview.visibility = View.VISIBLE
            } else {
                view.sold_textview.visibility = View.GONE
            }

            view.setOnClickListener { clickListener(realEstate) }
        }
    }


}


