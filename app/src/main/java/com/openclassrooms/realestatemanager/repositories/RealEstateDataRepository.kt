package com.openclassrooms.realestatemanager.repositories

import androidx.lifecycle.LiveData
import androidx.sqlite.db.SupportSQLiteQuery
import com.openclassrooms.realestatemanager.models.RealEstate
import com.openclassrooms.realestatemanager.database.RealEstateDao


class RealEstateDataRepository(private val realEstateDao: RealEstateDao) {


    // GET
    fun getAllRealEstates(): LiveData<List<RealEstate>> {
        return this.realEstateDao.getAllRealEstates()
    }

    fun getRealEstateById(id: Int): LiveData<RealEstate> {
        return this.realEstateDao.getRealEstateById(id)
    }

    // CREATE
    fun createRealEstate(realEstate: RealEstate) {
        realEstateDao.createRealEstate(realEstate)
    }

    // UPDATE
    fun updateRealEstate(realEstate: RealEstate) {
        realEstateDao.updateRealEstate(realEstate)
    }

    // USER SEARCH QUERY
    fun getRealEstateFromUserSearch(query: SupportSQLiteQuery): LiveData<List<RealEstate>> {

        return this.realEstateDao.getRealEstateFromUserSearch(query)
    }


}