package com.openclassrooms.realestatemanager.models

import android.content.ContentValues
import androidx.room.*
import java.io.Serializable


@Entity(tableName = "properties")
data class RealEstate(

        @PrimaryKey(autoGenerate = true) val propertyId: Long = 0,
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
        var agent: String? = null
        //var images: List<Uri>? = null
) : Serializable {


    companion object {

        fun fromContentValues(values: ContentValues): RealEstate {

            val realEstate = RealEstate()
            if (values.containsKey("type")) realEstate.type = values.getAsInteger("type")
            if (values.containsKey("description")) realEstate.description = values.getAsString("description")
            if (values.containsKey("street")) realEstate.address?.street = values.getAsString("street")
            if (values.containsKey("postalCode")) realEstate.address?.postalCode = values.getAsString("postalCode")
            if (values.containsKey("city")) realEstate.address?.city = values.getAsString("city")
            if (values.containsKey("latitude")) realEstate.latitude = values.getAsDouble("latitude")
            if (values.containsKey("longitude")) realEstate.longitude = values.getAsDouble("longitude")
            if (values.containsKey("surface")) realEstate.surface = values.getAsString("surface")
            if (values.containsKey("price")) realEstate.price = values.getAsString("price")
            if (values.containsKey("nbRooms")) realEstate.nbRooms = values.getAsString("nbRooms")
            if (values.containsKey("nbBedrooms")) realEstate.nbBedrooms = values.getAsString("nbBedrooms")
            if (values.containsKey("nbBathrooms")) realEstate.nbBathrooms = values.getAsString("nbBathrooms")
            if (values.containsKey("status")) realEstate.status = values.getAsBoolean("status")
            if (values.containsKey("creationDate")) realEstate.creationDate = values.getAsString("creationDate")
            if (values.containsKey("saleDate")) realEstate.saleDate = values.getAsString("saleDate")
            if (values.containsKey("agent")) realEstate.agent = values.getAsString("agent")

            return realEstate

        }
    }
}


data class Address(

        var street: String? = null,
        var city: String? = null,
        var postalCode: String? = null
) : Serializable






