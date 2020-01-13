package com.openclassrooms.realestatemanager

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.net.Uri
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.openclassrooms.realestatemanager.database.AppDatabase
import com.openclassrooms.realestatemanager.provider.RealEstateContentProvider.Companion.URI
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.hamcrest.core.IsNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ContentProviderTest {


    // FOR DATA
    private var contentResolver: ContentResolver? = null

    companion object {
        // DATA SET FOR TEST
        private const val REAL_ESTATE_ID: Long = 1
    }

    @Before
    fun setUp() {
        Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        contentResolver = InstrumentationRegistry.getContext().contentResolver
    }

    @Test
    fun insertAndGetRealEstate() {


        // Add object
        val uri: Uri? = contentResolver!!.insert(URI, generateRealEstate())
        // Test
        val cursor = contentResolver!!.query(ContentUris.withAppendedId(URI, REAL_ESTATE_ID),
                null, null, null, null)
        MatcherAssert.assertThat(cursor, IsNull.notNullValue())
        MatcherAssert.assertThat(cursor!!.moveToFirst(), Matchers.`is`(true))
        MatcherAssert.assertThat(cursor.getString(cursor.getColumnIndexOrThrow("description")), Matchers.`is`("Jolie maison"))
        MatcherAssert.assertThat(cursor.getString(cursor.getColumnIndexOrThrow("type")), Matchers.`is`("0"))
        MatcherAssert.assertThat(cursor.getString(cursor.getColumnIndexOrThrow("longitude")), Matchers.`is`("0.52"))
        MatcherAssert.assertThat(cursor.getString(cursor.getColumnIndexOrThrow("latitude")), Matchers.`is`("5.325"))
        MatcherAssert.assertThat(cursor.getString(cursor.getColumnIndexOrThrow("surface")), Matchers.`is`("150"))
        MatcherAssert.assertThat(cursor.getString(cursor.getColumnIndexOrThrow("price")), Matchers.`is`("200000"))
        MatcherAssert.assertThat(cursor.getString(cursor.getColumnIndexOrThrow("status")), Matchers.`is`("1"))
        MatcherAssert.assertThat(cursor.getString(cursor.getColumnIndexOrThrow("creationDate")), Matchers.`is`("15/12/2019"))
        MatcherAssert.assertThat(cursor.getString(cursor.getColumnIndexOrThrow("saleDate")), Matchers.`is`("10/01/2020"))
        MatcherAssert.assertThat(cursor.getString(cursor.getColumnIndexOrThrow("agent")), Matchers.`is`("Othman"))
        MatcherAssert.assertThat(cursor.getString(cursor.getColumnIndexOrThrow("nbRooms")), Matchers.`is`("5"))
        MatcherAssert.assertThat(cursor.getString(cursor.getColumnIndexOrThrow("nbBedrooms")), Matchers.`is`("3"))
        MatcherAssert.assertThat(cursor.getString(cursor.getColumnIndexOrThrow("nbBathrooms")), Matchers.`is`("2"))

        contentResolver!!.delete(uri!!, null, null)

        cursor.close()

    }


    private fun generateRealEstate(): ContentValues {
        val values = ContentValues()
        values.put("description", "Jolie maison")
        values.put("type", "0")
        values.put("longitude", "0.52")
        values.put("latitude", "5.325")
        values.put("surface", "150")
        values.put("price", "200000")
        values.put("status", "1")
        values.put("creationDate", "15/12/2019")
        values.put("saleDate", "10/01/2020")
        values.put("agent", "Othman")
        values.put("nbRooms", "5")
        values.put("nbBedrooms", "3")
        values.put("nbBathrooms", "2")
        return values
    }


}