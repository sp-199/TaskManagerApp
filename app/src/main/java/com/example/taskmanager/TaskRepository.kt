package com.example.taskmanager

import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDAO) {

    suspend fun insertTask(task: Task): Long {
        return taskDao.insertTask(task)
    }

    suspend fun getTasksByEmail(email: String): List<Task> {
        return taskDao.getTasksByEmail(email)
    }

    suspend fun deleteAllTasks(email: String) {
        taskDao.deleteAllTasks(email)
    }

    suspend fun deleteTask(taskId: Int) {
        taskDao.deleteTask(taskId)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun getTotalTasksCount(email:String):Flow<Int>{
        return taskDao.getTotalTasksCount(email)
    }

    suspend fun getCompletedTasksCount(email:String):Flow<Int>{
        return taskDao.getCompletedTasksCount(email)
    }
}