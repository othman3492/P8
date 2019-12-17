package com.openclassrooms.realestatemanager.database

import android.content.Context
import androidx.room.*
import com.openclassrooms.realestatemanager.models.RealEstate
import com.openclassrooms.realestatemanager.utils.Converters


@Database(
        entities = [RealEstate::class],
        version = 1,
        exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    // Return Dao
    abstract fun realEstateDao(): RealEstateDao


    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        // Ensure that no more than one database is created
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(context.applicationContext,
                                AppDatabase::class.java, "RealEstateDatabase.db")
                                .build()
                    }
                }
            }
            return instance
        }
    }

}