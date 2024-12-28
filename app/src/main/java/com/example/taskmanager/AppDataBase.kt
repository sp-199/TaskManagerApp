package com.example.taskmanager

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.taskmanager.User
import com.example.taskmanager.UserDAO

@Database(entities = [User::class, Task::class], version = 3)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDAO(): UserDAO
    abstract fun taskDAO(): TaskDAO
}