package com.openclassrooms.realestatemanager.models

import android.graphics.Bitmap
import android.net.Uri
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import java.util.*


@Entity(
        tableName = "properties",
        indices = [Index("creationDate")],

        foreignKeys = [ForeignKey(
                entity = User::class,
                parentColumns = arrayOf("userId"),
                childColumns = arrayOf("userId"),
                onDelete = CASCADE
        )])
data class RealEstate(

        @PrimaryKey(autoGenerate = true) val propertyId: Int = 0,
        val type: Int, //Enum<>,
        val description: String?,
        @Embedded val address: Address?,
        val latitude: Double?,
        val longitude: Double?,
        val surface: Int?,
        val price: Int?,
        val nbRooms: Int?,
        val nbBedrooms: Int?,
        val nbBathrooms: Int?,
        val status: Boolean?,
        val creationDate: String?,
        val saleDate: String?,
        val userId: Int
        //val images: List<Uri>?
)

data class Address(
        val street: String?,
        val state: String?,
        val city: String?,
        val postalCode: Int?
)