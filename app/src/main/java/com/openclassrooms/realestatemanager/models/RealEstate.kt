package com.openclassrooms.realestatemanager.models

import androidx.room.*
import androidx.room.ForeignKey.CASCADE


@Entity(tableName = "properties")
data class RealEstate(

        @PrimaryKey(autoGenerate = true) val propertyId: Int = 0,
        var type: Int? = null,
        var description: String? = null,
        @Embedded var address: Address? = null,
        var latitude: Double? = null,
        var longitude: Double? = null,
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
)


data class Address(

        var street: String? = null,
        var city: String? = null,
        var postalCode: String? = null
)






