package com.openclassrooms.realestatemanager.repositories

import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.models.RealEstate
import com.openclassrooms.realestatemanager.database.RealEstateDao


class RealEstateDataRepository(private val realEstateDao: RealEstateDao) {


    // GET
    fun getAllRealEstates(): LiveData<List<RealEstate>> {
        return this.realEstateDao.getAllRealEstates()
    }

    fun getRealEstatesByUser(userId: Int): LiveData<List<RealEstate>> {
        return this.realEstateDao.getRealEstatesByUser(userId)
    }

    fun getRealEstateById(id: Int): LiveData<RealEstate> {
        return this.realEstateDao.getRealEstateById(id)
    }

    // CREATE
    fun createRealEstate(realEstate: RealEstate) {
        return this.realEstateDao.createRealEstate(realEstate)
    }

    // UPDATE
    fun updateRealEstate(realEstate: RealEstate) {
        return this.realEstateDao.updateRealEstate(realEstate)
    }

    // DELETE
    fun deleteRealEstate(realEstate: RealEstate) {
        return this.realEstateDao.deleteRealEstate(realEstate)
    }


}