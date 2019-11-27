package com.openclassrooms.realestatemanager.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.openclassrooms.realestatemanager.models.User


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