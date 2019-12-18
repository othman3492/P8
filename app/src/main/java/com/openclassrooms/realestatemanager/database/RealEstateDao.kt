package com.openclassrooms.realestatemanager.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.models.RealEstate


@Dao
interface RealEstateDao {

    @Query("SELECT * FROM properties")
    fun getAllRealEstates(): LiveData<List<RealEstate>>

    @Query("SELECT * FROM properties WHERE propertyId = :id")
    fun getRealEstateById(id: Int): LiveData<RealEstate>

    @Insert
    fun createRealEstate(realEstate: RealEstate): Long

    @Update
    fun updateRealEstate(realEstate: RealEstate): Int

    @Query("DELETE FROM properties WHERE propertyId = :id")
    fun deleteRealEstate(id: Long): Int

    @Query("SELECT * FROM properties")
    fun getRealEstatesWithCursor(): Cursor


    @Query("SELECT * FROM properties WHERE street = :street " +
            "AND postalCode = :postalCode " +
            "AND city = :city " +
            "AND agent = :agent " +
            "AND type = :type " +
            "AND price BETWEEN :minPrice AND :maxPrice " +
            "AND surface BETWEEN :minSurface AND :maxSurface " +
            "AND nbRooms BETWEEN :minNbRooms AND :maxNbRooms " +
            "AND nbBedrooms BETWEEN :minNbBedrooms AND :maxNbBedrooms " +
            "AND nbBathrooms BETWEEN :minNbBathrooms AND :maxNbBathrooms " +
            "AND status = :status")
    fun getRealEstateFromUserSearch(street: String?,
                                    postalCode: String?,
                                    city: String?,
                                    agent: String?,
                                    type: Int?,
                                    minPrice: Int?, maxPrice: Int?,
                                    minSurface: Int?, maxSurface: Int?,
                                    minNbRooms: Int?, maxNbRooms: Int?,
                                    minNbBedrooms: Int?, maxNbBedrooms: Int?,
                                    minNbBathrooms: Int?, maxNbBathrooms: Int?,
                                    status: Boolean
    ): LiveData<List<RealEstate>>
}

