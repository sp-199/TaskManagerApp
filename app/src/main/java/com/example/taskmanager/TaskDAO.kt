package com.example.taskmanager

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

    @Query("SELECT * FROM tasks WHERE email = :email")
    suspend fun getTasksByEmail(email: String): List<Task>

    @Query("DELETE  FROM tasks WHERE email = :email")
    suspend fun deleteAllTasks(email: String
    )
    @Query("DELETE FROM tasks WHERE taskId = :taskId")
    suspend fun deleteTask(taskId: Int)

    @Update
    suspend fun updateTask(task: Task)
}
