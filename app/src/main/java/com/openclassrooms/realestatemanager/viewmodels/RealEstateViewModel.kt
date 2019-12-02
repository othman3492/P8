package com.openclassrooms.realestatemanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.models.RealEstate
import com.openclassrooms.realestatemanager.repositories.RealEstateDataRepository
import java.util.concurrent.Executor

class RealEstateViewModel(private val realEstateDataSource: RealEstateDataRepository,
                          private val executor: Executor) : ViewModel() {


    // REAL ESTATE

    fun getAllRealEstates(): LiveData<List<RealEstate>> {
        return realEstateDataSource.getAllRealEstates()
    }

    fun getRealEstateById(id: Int): LiveData<RealEstate> {
        return realEstateDataSource.getRealEstateById(id)
    }

    fun createRealEstate(realEstate: RealEstate) {
        executor.execute { realEstateDataSource.createRealEstate(realEstate) }
    }

    fun updateRealEstate(realEstate: RealEstate) {
        executor.execute { realEstateDataSource.updateRealEstate(realEstate) }
    }

    fun deleteRealEstate(realEstate: Long) {
        executor.execute { realEstateDataSource.deleteRealEstate(realEstate) }
    }

}