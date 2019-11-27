package com.openclassrooms.realestatemanager.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.models.RealEstate
import com.openclassrooms.realestatemanager.models.User


@Dao
interface RealEstateDao {

    @Query("SELECT * FROM properties")
    fun getAllRealEstates(): LiveData<List<RealEstate>>

    @Query("SELECT * FROM properties WHERE propertyId = :id")
    fun getRealEstateById(id: Int): LiveData<RealEstate>

    @Insert
    fun createRealEstate(realEstate: RealEstate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateRealEstate(realEstate: RealEstate)

    @Delete
    fun deleteRealEstate(realEstate: RealEstate)
}

