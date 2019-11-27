package com.openclassrooms.realestatemanager.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.repositories.RealEstateDataRepository
import com.openclassrooms.realestatemanager.repositories.UserDataRepository
import java.util.concurrent.Executor


class ViewModelFactory(private val realEstateDataSource: RealEstateDataRepository,
                       private val userDataSource: UserDataRepository,
                       private val executor: Executor) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RealEstateViewModel::class.java)) {
            return RealEstateViewModel(realEstateDataSource, userDataSource, executor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}