package com.openclassrooms.realestatemanager.viewmodels

import android.content.Context
import androidx.room.RoomDatabase
import com.openclassrooms.realestatemanager.database.*
import com.openclassrooms.realestatemanager.repositories.RealEstateDataRepository
import com.openclassrooms.realestatemanager.repositories.UserDataRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class Injection {

    private fun provideRealEstateDataSource(context: Context): RealEstateDataRepository {
        val database = AppDatabase.getInstance(context)
        return RealEstateDataRepository(database!!.realEstateDao())
    }

    private fun provideUserDataSource(context: Context): UserDataRepository {
        val database = AppDatabase.getInstance(context)
        return UserDataRepository(database!!.userDao())
    }


    private fun provideExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val dataSourceRealEstate = provideRealEstateDataSource(context)
        val dataSourceUser = provideUserDataSource(context)
        val executor = provideExecutor()
        return ViewModelFactory(dataSourceRealEstate, dataSourceUser, executor)
    }
}