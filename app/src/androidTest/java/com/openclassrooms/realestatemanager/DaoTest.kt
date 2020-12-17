package com.openclassrooms.realestatemanager

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.openclassrooms.realestatemanager.model.RealEstate
import com.openclassrooms.realestatemanager.database.AppDatabase
import com.openclassrooms.realestatemanager.database.RealEstateDao
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
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
    private lateinit var database: AppDatabase
    private lateinit var dao: RealEstateDao

    @Before
    @Throws(Exception::class)
    fun setUp() {

        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context,
                AppDatabase::class.java)
                .allowMainThreadQueries().build()
        dao = database.realEstateDao()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {

        database.close()
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertAndGetProperties() = runBlockingTest {

        // Add user & properties
        dao.createRealEstate(REAL_ESTATE_1)

        // Test
        val properties = dao.getAllRealEstates().getOrAwaitValue()
        TestCase.assertTrue(properties.contains(REAL_ESTATE_1))
    }


    @Test
    @Throws(InterruptedException::class)
    fun insertAndUpdateProperty() = runBlockingTest {

        // Add property
        dao.createRealEstate(REAL_ESTATE_1)

        // Get property and update it
        val propertyToUpdate = dao.getRealEstateById(REAL_ESTATE_1.propertyId.toInt()).getOrAwaitValue()
        propertyToUpdate.status = true
        dao.updateRealEstate(propertyToUpdate)

        // Test
        val properties = dao.getAllRealEstates().getOrAwaitValue()
        TestCase.assertTrue(properties[0].status != null)
    }


    @Test
    @Throws(InterruptedException::class)
    fun insertAndDeleteProperty() = runBlockingTest {

        // Add property
        dao.createRealEstate(REAL_ESTATE_2)

        // Get property and delete it
        val propertyToDelete = dao.getRealEstateById(REAL_ESTATE_2.propertyId.toInt()).getOrAwaitValue()
        dao.deleteRealEstate(propertyToDelete)

        // Test
        val properties = dao.getAllRealEstates().getOrAwaitValue()
        TestCase.assertTrue(properties.isEmpty())
    }

}
