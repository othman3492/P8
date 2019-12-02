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
}

