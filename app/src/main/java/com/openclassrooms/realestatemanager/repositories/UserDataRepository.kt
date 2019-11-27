package com.openclassrooms.realestatemanager.repositories

import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.database.UserDao
import com.openclassrooms.realestatemanager.models.User


class UserDataRepository(private val userDao: UserDao) {


    // GET
    fun getAllUsers(): LiveData<List<User>> {
        return this.userDao.getAllUsers()
    }

    fun getUserById(id: Int): LiveData<User> {
        return this.userDao.getUserById(id)
    }

    // CREATE
    fun createUser(user: User) {
        return this.userDao.createUser(user)
    }

    // DELETE
    fun deleteUser(user: User) {
        return this.userDao.deleteUser(user)
    }


}