package com.openclassrooms.realestatemanager.controllers.fragments


import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.ContextCompat.getExternalFilesDirs
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.controllers.activities.AddEditActivity
import com.openclassrooms.realestatemanager.models.RealEstate
import com.openclassrooms.realestatemanager.viewmodels.RealEstateViewModel
import com.openclassrooms.realestatemanager.views.ElementAdapter
import com.openclassrooms.realestatemanager.views.PhotoAdapter
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_photo.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class PhotoFragment : DialogFragment() {


    private val IMAGE_PICK_CODE = 1
    private val GALLERY_PERMISSION_CODE = 11
    private val CAMERA_PERMISSION_CODE = 21
    private val CAMERA_CODE = 31

    private lateinit var photoPath: Uri
    private lateinit var adapter: PhotoAdapter

    private var realEstate: RealEstate = RealEstate()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get current Real Estate data if it exists
        realEstate = this.arguments!!.getSerializable("PHOTO_REAL_ESTATE") as RealEstate
        Toast.makeText(activity, "${realEstate!!.address}", Toast.LENGTH_SHORT).show() // TEST


        configureButtons()
        configureRecyclerView()

    }


    // Configure RecyclerView and assign the click handler to the Adapter
    private fun configureRecyclerView() {

        adapter = PhotoAdapter(requireContext(), realEstate!!)
        photo_recycler_view.adapter = adapter
        photo_recycler_view.layoutManager = LinearLayoutManager(activity)
        photo_recycler_view.addItemDecoration(DividerItemDecoration(photo_recycler_view.context, DividerItemDecoration.VERTICAL))
    }


    private fun configureButtons() {

        add_photo_button.setOnClickListener { checkPermissionForGallery() }
        take_photo_button.setOnClickListener { checkPermissionForCamera() }
    }


    // Launch camera and create image path
    @Throws(IOException::class)
    private fun accessCamera() {

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(context!!.packageManager) != null) {

            val photoFile = createImageFile()

            if (photoFile != null) {
                photoPath = FileProvider.getUriForFile(activity!!, "com.openclassrooms.realestatemanager.fileprovider", photoFile)

                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoPath)
                startActivityForResult(cameraIntent, CAMERA_CODE)
            }
        }
    }


    // Create image file
    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File? {

        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "estate_" + timeStamp + "_"
        val storageDir: File = getExternalFilesDirs(activity!!, Environment.DIRECTORY_PICTURES)[0]

        return File.createTempFile(
                imageFileName, ".jpg", storageDir
        )
    }


    // Ask for permission to use camera
    private fun checkPermissionForCamera() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                // Permission denied
                val permissions = arrayOf(android.Manifest.permission.CAMERA)
                // Request permission
                requestPermissions(permissions, CAMERA_PERMISSION_CODE)
            } else {
                // Permission granted
                accessCamera()
            }
        } else {
            // No permission needed if API < 23
            accessCamera()
        }

    }


    // Ask for permission to pick image from gallery
    private fun checkPermissionForGallery() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                // Permission denied
                val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                // Request permission
                requestPermissions(permissions, GALLERY_PERMISSION_CODE)
            } else {
                // Permission granted
                pickPhotoFromGallery()
            }
        } else {
            // No permission needed if API < 23
            pickPhotoFromGallery()
        }

    }


    // Handle request permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {

            GALLERY_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    pickPhotoFromGallery()
                } else {
                    // Permission denied
                    Toast.makeText(activity, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }

            CAMERA_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    accessCamera()
                } else {
                    // Permission denied
                    Toast.makeText(activity, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    // Handle picked image result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {

            realEstate.imageList.add(data?.data.toString())
            adapter.notifyDataSetChanged()

        } else if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_CODE) {

            realEstate.imageList.add(photoPath.toString())
            adapter.notifyDataSetChanged()
        }
    }


    // Search gallery for photos
    private fun pickPhotoFromGallery() {

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }


    // Save modifications in real estate photo list and pass it to Activity via intent
    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        val updateIntent = Intent(activity, AddEditActivity::class.java)
        updateIntent.putExtra("PHOTO_UPDATE_REAL_ESTATE", realEstate)
    }
}