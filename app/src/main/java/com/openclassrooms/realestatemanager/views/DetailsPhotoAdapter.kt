package com.openclassrooms.realestatemanager.views

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.models.RealEstate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.details_list_photo_layout.view.*


class DetailsPhotoAdapter(val context: Context, val realEstate: RealEstate) :
        RecyclerView.Adapter<DetailsPhotoAdapter.DetailsPhotoViewHolder>() {


    override fun getItemCount() = realEstate.imageList.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsPhotoViewHolder {

        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.details_list_photo_layout, parent, false)
        return DetailsPhotoViewHolder(v)
    }

    // Populate ViewHolder with data depending on the position in the list
    override fun onBindViewHolder(holder: DetailsPhotoViewHolder, position: Int) {

        holder.bind(Uri.parse(realEstate.imageList[position]))
    }


    class DetailsPhotoViewHolder(v: View) : RecyclerView.ViewHolder(v) {


        private var view: View = v


        // Assign data to the views
        fun bind(photo: Uri) {

            Picasso.get().load(photo).into(view.details_photo_image)
        }


    }
}





