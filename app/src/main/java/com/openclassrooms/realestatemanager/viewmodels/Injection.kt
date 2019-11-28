package com.openclassrooms.realestatemanager.viewmodels

import android.content.Context
import com.openclassrooms.realestatemanager.database.*
import com.openclassrooms.realestatemanager.repositories.RealEstateDataRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors


object Injection {

    private fun provideRealEstateDataSource(context: Context): RealEstateDataRepository {
        val database = AppDatabase.getInstance(context)
        return RealEstateDataRepository(database!!.realEstateDao())
    }


    private fun provideExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val dataSourceRealEstate = provideRealEstateDataSource(context)
        val executor = provideExecutor()
        return ViewModelFactory(dataSourceRealEstate, executor)
    }
}