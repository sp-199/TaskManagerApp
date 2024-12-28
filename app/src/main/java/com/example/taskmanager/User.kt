package com.example.taskmanager

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "users")
data class User(
    @PrimaryKey @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "name") val name: String?="",
    @ColumnInfo(name = "lastName") val lastName: String?="",
    @ColumnInfo(name = "age") val age: Int?=0,
    @ColumnInfo(name = "phoneNumber") val phoneNumber: String?="",
    @ColumnInfo(name = "password") val password: String?=""
)