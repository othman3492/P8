package com.openclassrooms.realestatemanager.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
        tableName = "users",
        indices = [Index("lastName")]
)
data class User(
        @PrimaryKey(autoGenerate = true) val userId: Int = 0,
        val firstName: String,
        val lastName: String
)