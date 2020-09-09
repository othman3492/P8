package com.openclassrooms.realestatemanager.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.openclassrooms.realestatemanager.model.RealEstate


@Dao
interface RealEstateDao {


    // GET
    @Query("SELECT * FROM properties")
    fun getAllRealEstates(): LiveData<List<RealEstate>>

    @Query("SELECT * FROM properties WHERE propertyId = :id")
    fun getRealEstateById(id: Int): LiveData<RealEstate>

    @Query("SELECT * FROM properties")
    fun getRealEstatesWithCursor(): Cursor

    @RawQuery(observedEntities = [RealEstate::class])
    fun getRealEstateFromUserSearch(query: SupportSQLiteQuery): LiveData<List<RealEstate>>

    // CREATE
    @Insert
    suspend fun createRealEstate(realEstate: RealEstate): Long

    // UPDATE
    @Update
    suspend fun updateRealEstate(realEstate: RealEstate): Int

    // DELETE
    @Delete
    suspend fun deleteRealEstate(realEstate: RealEstate): Int
}

