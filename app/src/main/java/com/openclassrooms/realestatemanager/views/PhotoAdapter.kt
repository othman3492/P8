package com.openclassrooms.realestatemanager.views

import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.models.RealEstate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_element_layout.view.*
import kotlinx.android.synthetic.main.list_photo_layout.view.*


// Create Adapter with a click listener (parameter RealEstate, return nothing)
class PhotoAdapter(val context: Context) :
        RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {


    private var photos: List<Uri> = ArrayList()


    override fun getItemCount() = photos.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {

        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.list_photo_layout, parent, false)
        return PhotoViewHolder(v, parent.context)
    }

    // Populate ViewHolder with data depending on the position in the list, and configure the click
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {

        holder.bind(photos[position])
    }


    fun updateData(list: List<Uri>) {

        this.photos = list
        this.notifyDataSetChanged()
    }


    class PhotoViewHolder(v: View, private var context: Context) : RecyclerView.ViewHolder(v) {


        private var view: View = v


        // Assign data to the views and handle click events through a function parameter
        fun bind(photo: Uri) {

            Picasso.get().load(photo).into(view.photo_list_image)

        }
    }


}


