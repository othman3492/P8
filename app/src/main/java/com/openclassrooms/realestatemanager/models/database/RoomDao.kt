package com.openclassrooms.realestatemanager.models.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.models.RealEstate
import com.openclassrooms.realestatemanager.models.User


@Dao
interface PropertyDao {

    @Query("SELECT * FROM properties")
    fun getAllProperties(): LiveData<List<RealEstate>>

    @Query("SELECT * FROM properties WHERE propertyId = :id")
    fun getPropertyById(id: Int): LiveData<RealEstate>

    @Insert
    fun createProperty(realEstate: RealEstate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateProperty(realEstate: RealEstate)

    @Delete
    fun deleteProperty(realEstate: RealEstate)
}


@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUserById(id: Int): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createUser(user: User)

    @Delete
    fun deleteUser(user: User)
}