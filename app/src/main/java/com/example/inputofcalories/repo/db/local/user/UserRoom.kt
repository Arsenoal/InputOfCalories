package com.example.inputofcalories.repo.db.local.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserRoom(
    @PrimaryKey
    @ColumnInfo(name = "userId") val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "type") val type: Int
)