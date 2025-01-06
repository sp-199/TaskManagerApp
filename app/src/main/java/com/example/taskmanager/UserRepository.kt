package com.example.taskmanager

import androidx.room.Query

class UserRepository(private val userDAO: UserDAO) {
    suspend fun insertUser(user: User) {
        userDAO.insertUser(user)
    }

    suspend fun updateUser(user:User){
        userDAO.updateUser(user)
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

    suspend fun updateName(name: String, email: String) {
        userDAO.updateName(name, email)
    }

    suspend fun updateLastname(lastname: String, email: String) {
        userDAO.updateLastname(lastname, email)
    }

    suspend fun updateAge(age: Int, email: String) {
        userDAO.updateAge(age, email)
    }

    suspend fun updatePhoneNumber(phoneNumber: String, email:String) {
        userDAO.updatePhoneNumber(phoneNumber, email)
    }


}