package com.example.taskmanager

import androidx.room.*

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT name FROM users WHERE email= :email")
    suspend fun getName(email:String): String?

    @Query("SELECT Lastname FROM users WHERE email= :email")
    suspend fun getLastname(email:String): String?

    @Query("SELECT age FROM users WHERE email= :email")
    suspend fun getAge(email:String): Int?

    @Query("SELECT phoneNumber FROM users WHERE email= :email")
    suspend fun getPhoneNumber(email:String): String?

    @Query("DELETE FROM users WHERE email = :email")
    suspend fun deleteUserByEmail(email: String)

    @Query("SELECT password FROM users WHERE email = :email LIMIT 1")
    suspend fun getPasswordByEmail(email: String): String?

    @Query("UPDATE users SET name = :name WHERE email = :email")
    suspend fun updateName(email: String, name: String)

    @Query("UPDATE users SET lastname = :lastname WHERE email = :email")
    suspend fun updateLastname(email: String, lastname: String)

    @Query("UPDATE users SET age = :age WHERE email = :email")
    suspend fun updateAge(email: String, age: Int)

    @Query("UPDATE users SET phoneNumber = :phoneNumber WHERE email = :email")
    suspend fun updatePhoneNumber(email: String, phoneNumber: String)
}