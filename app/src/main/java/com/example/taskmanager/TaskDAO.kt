package com.example.taskmanager

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

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

    @Query("SELECT COUNT(*) FROM tasks WHERE email = :email")
    fun getTotalTasksCount(email: String): Flow<Int>

    @Query("SELECT COUNT(*) FROM tasks WHERE email = :email AND status = 1")
    fun getCompletedTasksCount(email: String): Flow<Int>

    @Update
    suspend fun updateTask(task: Task)
}
