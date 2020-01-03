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
import kotlinx.android.synthetic.main.list_photo_layout.view.*


class PhotoAdapter(val context: Context, val realEstate: RealEstate) :
        RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {


    override fun getItemCount() = realEstate.imageList.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {

        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.list_photo_layout, parent, false)
        return PhotoViewHolder(v)
    }

    // Populate ViewHolder with data depending on the position in the list
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {

        holder.bind(Uri.parse(realEstate.imageList[position]))

        // Configure delete button
        holder.itemView.photo_list_delete_button.setOnClickListener {

            realEstate.imageList.removeAt(position)
            notifyItemRemoved(position)

        }
    }


    class PhotoViewHolder(v: View) : RecyclerView.ViewHolder(v) {


        private var view: View = v


        // Assign data to the views and configure delete button
        fun bind(photo: Uri) {

            Picasso.get().load(photo).into(view.photo_list_image)
        }


    }
}





