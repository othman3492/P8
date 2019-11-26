package com.openclassrooms.realestatemanager.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class User(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        val firstName: String,
        val lastName: String
)