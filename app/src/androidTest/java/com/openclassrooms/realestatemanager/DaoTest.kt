package com.openclassrooms.realestatemanager

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.openclassrooms.realestatemanager.models.RealEstate
import com.openclassrooms.realestatemanager.models.User
import com.openclassrooms.realestatemanager.database.AppDatabase
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DaoTest {

    companion object {
        // Data set for test
        private const val USER_ID = 1
        private val USER_DEMO = User(USER_ID, "Brad", "Pitt")
        private val REAL_ESTATE_1 = RealEstate(1, "Maison",
                null, null, null, "200", "200 000",
                "5", "2", "1", false, null,
                null, USER_ID)
        private val REAL_ESTATE_2 = RealEstate(2, "Appartement",
                null, null, null, "80", "120 000",
                "3", "1", "1", false, null,
                null, USER_ID)
    }

    @Rule
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
    fun insertAndGetUser() {

        // Add user
        database!!.userDao().createUser(USER_DEMO)

        // Test
        val (id, firstName) = LiveDataTestUtil.getValue(database!!.userDao().getUserById(USER_ID))
        TestCase.assertTrue(firstName == USER_DEMO.firstName && id == USER_ID)
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertAndGetProperties() {

        // Add user & properties
        database!!.userDao().createUser(USER_DEMO)
        database!!.realEstateDao().createRealEstate(REAL_ESTATE_1)
        database!!.realEstateDao().createRealEstate(REAL_ESTATE_2)

        // Test
        val properties = LiveDataTestUtil.getValue(database!!.realEstateDao().getAllRealEstates())
        TestCase.assertEquals(2, properties.size)
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertAndUpdateProperty() {

        // Add user & property
        database!!.userDao().createUser(USER_DEMO)
        database!!.realEstateDao().createRealEstate(REAL_ESTATE_1)

        // Get property and update it
        val realEstateToUpdate = LiveDataTestUtil.getValue(database!!.realEstateDao().getAllRealEstates())[0]
        realEstateToUpdate.status = true
        database!!.realEstateDao().updateRealEstate(realEstateToUpdate)

        // Test
        val properties = LiveDataTestUtil.getValue(database!!.realEstateDao().getAllRealEstates())
        TestCase.assertTrue(properties.size == 1 && properties[0].status!!)
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertAndDeleteProperty() {

        // Add user & property
        database!!.userDao().createUser(USER_DEMO)
        database!!.realEstateDao().createRealEstate(REAL_ESTATE_2)

        // Get property and delete it
        val realEstateToDelete = LiveDataTestUtil.getValue(database!!.realEstateDao().getAllRealEstates())[0]
        database!!.realEstateDao().deleteRealEstate(realEstateToDelete)
    }
}