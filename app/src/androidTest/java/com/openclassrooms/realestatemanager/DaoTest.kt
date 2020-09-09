package com.openclassrooms.realestatemanager

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.openclassrooms.realestatemanager.model.RealEstate
import com.openclassrooms.realestatemanager.database.AppDatabase
import junit.framework.TestCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DaoTest {

    companion object {
        // Data set for test
        private val REAL_ESTATE_1 = RealEstate(1, 1,
                null, null, null, null, null,
                5, 2, 1, 1, null,
                null)
        private val REAL_ESTATE_2 = RealEstate(2, 2,
                null, null, null, null, null,
                3, 1, 1, 1, null,
                null)
    }

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private var database: AppDatabase? = null

    @Before
    @Throws(Exception::class)
    fun initDatabase() {

        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context,
                AppDatabase::class.java)
                .allowMainThreadQueries().build()
    }

    @After
    @Throws(Exception::class)
    fun closeDatabase() {

        database!!.close()
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertAndGetProperties() {

        CoroutineScope(IO).launch {
            // Add user & properties
            database!!.realEstateDao().createRealEstate(REAL_ESTATE_1)
            database!!.realEstateDao().createRealEstate(REAL_ESTATE_2)

        }

        // Test
        val properties = LiveDataTestUtil.getValue(database!!.realEstateDao().getAllRealEstates())
        TestCase.assertEquals(2, properties.size)
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertAndUpdateProperty() {

        CoroutineScope(IO).launch {
            // Add property
            database!!.realEstateDao().createRealEstate(REAL_ESTATE_1)

            // Get property and update it
            val realEstateToUpdate = LiveDataTestUtil.getValue(database!!.realEstateDao().getAllRealEstates())[0]
            realEstateToUpdate.status = true
            database!!.realEstateDao().updateRealEstate(realEstateToUpdate)

        }
        // Test
        val properties = LiveDataTestUtil.getValue(database!!.realEstateDao().getAllRealEstates())
        TestCase.assertTrue(properties.size == 1 && properties[0].status!!)
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertAndDeleteProperty() {

        CoroutineScope(IO).launch {

            // Add property
            database!!.realEstateDao().createRealEstate(REAL_ESTATE_2)

            // Get property and delete it
            val realEstateToDelete = LiveDataTestUtil.getValue(database!!.realEstateDao().getAllRealEstates())[0]
            database!!.realEstateDao().deleteRealEstate(realEstateToDelete)
        }
    }
}