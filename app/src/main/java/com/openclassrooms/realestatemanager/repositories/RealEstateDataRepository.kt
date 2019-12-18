package com.openclassrooms.realestatemanager.repositories

import androidx.lifecycle.LiveData
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

    // DELETE
    fun deleteRealEstate(id: Long) {
        realEstateDao.deleteRealEstate(id)
    }


    // USER SEARCH QUERY
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
    ): LiveData<List<RealEstate>> {

        return this.realEstateDao.getRealEstateFromUserSearch(street, postalCode, city, agent, type,
                minPrice, maxPrice, minSurface, maxSurface, minNbRooms, maxNbRooms,
                minNbBedrooms, maxNbBedrooms, minNbBathrooms, maxNbBathrooms, status)
    }


}