package com.example.taskmanager

import androidx.room.Query

class UserRepository(private val userDAO: UserDAO) {
    suspend fun insertUser(user: User) {
        userDAO.insertUser(user)
    }
    suspend fun deleteUser(user: User) {
        userDAO.deleteUser(user)
    }

    suspend fun deleteUserByEmail(email: String) {
        userDAO.deleteUserByEmail(email)
    }

    suspend fun getPassword(email: String): String? {
        return userDAO.getPasswordByEmail(email)
    }

    suspend fun getName(email:String): String?{
        return userDAO.getName(email)
    }

    suspend fun getLastname(email:String): String?{
        return userDAO.getLastname(email)
    }

    suspend fun getAge(email:String): Int?{
        return userDAO.getAge(email)
    }

    suspend fun getPhoneNumber(email:String): String?{
        return userDAO.getPhoneNumber(email)
    }

    suspend fun updateName(email: String, name: String) {
        userDAO.updateName(email, name)
    }

    suspend fun updateLastname(email: String, lastname: String) {
        userDAO.updateLastname(email, lastname)
    }

    suspend fun updateAge(email: String, age: Int) {
        userDAO.updateAge(email, age)
    }

    suspend fun updatePhoneNumber(email: String, phoneNumber: String) {
        userDAO.updatePhoneNumber(email, phoneNumber)
    }


}