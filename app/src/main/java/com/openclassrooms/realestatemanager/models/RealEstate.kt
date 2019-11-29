package com.openclassrooms.realestatemanager.models

import androidx.room.*
import java.io.Serializable


@Entity(tableName = "properties")
data class RealEstate(

        @PrimaryKey(autoGenerate = true) val propertyId: Int = 0,
        var type: Int? = null,
        var description: String? = null,
        @Embedded var address: Address? = null,
        var latitude: Double? = 48.8566,
        var longitude: Double? = 2.3522,
        var surface: String? = null,
        var price: String? = null,
        var nbRooms: String? = null,
        var nbBedrooms: String? = null,
        var nbBathrooms: String? = null,
        var status: Boolean? = null,
        var creationDate: String? = null,
        var saleDate: String? = null,
        @ColumnInfo(name = "userId") var userId: Int? = null
        //var images: List<Uri>? = null
) : Serializable


data class Address(

        var street: String? = null,
        var city: String? = null,
        var postalCode: String? = null
) : Serializable






