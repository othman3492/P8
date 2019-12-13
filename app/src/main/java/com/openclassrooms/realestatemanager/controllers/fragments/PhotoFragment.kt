package com.openclassrooms.realestatemanager.controllers.fragments


import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.DialogFragment
import com.openclassrooms.realestatemanager.R
import kotlinx.android.synthetic.main.fragment_photo.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class PhotoFragment : DialogFragment() {

    private val IMAGE_PICK_CODE = 1
    private val GALLERY_PERMISSION_CODE = 11
    private val CAMERA_PERMISSION_CODE = 21
    private val CAMERA_CODE = 31


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureButtons()
    }

    private fun configureRecyclerView() {

    }


    private fun configureButtons() {

        add_photo_button.setOnClickListener { checkPermissionForGallery() }
        take_photo_button.setOnClickListener { checkPermissionForCamera() }
    }


    // Launch camera
    private fun accessCamera() {


        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, CAMERA_CODE)
        startActivityForResult(cameraIntent, CAMERA_CODE)

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

            image_test.setImageURI(data?.data)

        } else if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_CODE) {

            image_test.setImageBitmap(data!!.extras!!.get("data") as Bitmap)
        }
    }


    // Search gallery for photos
    private fun pickPhotoFromGallery() {

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }
}