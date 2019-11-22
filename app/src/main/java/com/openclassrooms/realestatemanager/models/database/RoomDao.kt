package com.openclassrooms.realestatemanager.models.database

import androidx.room.*
import com.openclassrooms.realestatemanager.models.RealEstate
import com.openclassrooms.realestatemanager.models.User


@Dao
interface PropertyDao {

    @Query("SELECT * FROM properties")
    suspend fun getAllProperties(): List<RealEstate>

    @Query("SELECT * FROM properties WHERE propertyId = :id")
    suspend fun getPropertyById(id: Int): RealEstate

    @Insert
    suspend fun createProperty(realEstate: RealEstate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateProperty(realEstate: RealEstate)

    @Delete
    suspend fun deleteProperty(realEstate: RealEstate)
}


@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT * FROM users WHERE userId = :id")
    suspend fun getUserById(id: Int): User

    @Insert
    suspend fun createUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)
}