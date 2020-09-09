package com.openclassrooms.realestatemanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.sqlite.db.SupportSQLiteQuery
import com.openclassrooms.realestatemanager.model.RealEstate
import com.openclassrooms.realestatemanager.repositories.RealEstateDataRepository
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

class RealEstateViewModel(private val realEstateDataSource: RealEstateDataRepository,
                          private val executor: Executor) : ViewModel() {


    // GET
    fun getAllRealEstates(): LiveData<List<RealEstate>> {
        return realEstateDataSource.getAllRealEstates()
    }

    fun getRealEstateById(id: Int): LiveData<RealEstate> {
        return realEstateDataSource.getRealEstateById(id)
    }

    fun getRealEstateFromUserSearch(query: SupportSQLiteQuery): LiveData<List<RealEstate>> {
        return realEstateDataSource.getRealEstateFromUserSearch(query)

    }

    // CREATE
    fun createRealEstate(realEstate: RealEstate) {
        executor.execute {
            viewModelScope.launch {
                realEstateDataSource.createRealEstate(realEstate)
            }
        }
    }

    // UPDATE
    fun updateRealEstate(realEstate: RealEstate) {
        executor.execute {
            viewModelScope.launch {
                realEstateDataSource.updateRealEstate(realEstate)
            }
        }
    }


}